  #!/bin/bash
  # Script to generate release notes for Higress projects

  echo "Fetching GitHub generated release notes for ${GITHUB_REPO_OWNER}/${GITHUB_REPO_NAME}..."
  curl -L \
    -X POST \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: Bearer ${GITHUB_PERSONAL_ACCESS_TOKEN}" \
    -H "X-GitHub-Api-Version: 2022-11-28" \
    "https://api.github.com/repos/${GITHUB_REPO_OWNER}/${GITHUB_REPO_NAME}/releases/generate-notes" \
    -d "{\"tag_name\":\"${RELEASE_VERSION}\"}" \
    -o github_notes.json

  echo "Extracting PR numbers from ${GITHUB_REPO_OWNER}/${GITHUB_REPO_NAME} release notes..."
  PR_NUMS=$(cat github_notes.json | grep -o "https://github.com/${GITHUB_REPO_OWNER}/${GITHUB_REPO_NAME}/pull/[0-9]*" | grep -o "[0-9]*$" | sort -n | uniq | tr '\n' ',')
  PR_NUMS=${PR_NUMS%,}

  echo "Extracted PR numbers for ${GITHUB_REPO_OWNER}/${GITHUB_REPO_NAME}: ${PR_NUMS}"

  if [ -n "$PR_NUMS" ]; then
    echo "Generating detailed release notes for ${GITHUB_REPO_OWNER}/${GITHUB_REPO_NAME}..."
    cd higress-report-agent
    python report_main.py --mode 2 --choice 2 --pr_nums ${PR_NUMS}
    cp report.md ../
    cp report.EN.md ../
    cd ..
  else
    echo "No PRs found for ${GITHUB_REPO_OWNER}/${GITHUB_REPO_NAME}, creating empty report files"
    echo "" >report.md
    echo "" >report.EN.md
  fi

  echo "# ${REPORT_TITLE}" >>release-notes/${RELEASE_VERSION}/README_ZH.md
  sed 's/# Release Notes//' report.md >>release-notes/${RELEASE_VERSION}/README_ZH.md

  echo "# ${REPORT_TITLE}" >>release-notes/${RELEASE_VERSION}/README.md
  sed 's/# Release Notes//' report.EN.md >>release-notes/${RELEASE_VERSION}/README.md

  echo "${REPORT_TITLE} release notes saved to release-notes/${RELEASE_VERSION}/"
  EOF
  chmod +x generate_release_report.sh
