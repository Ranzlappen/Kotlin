#!/usr/bin/env bash
#
# Template customizer (idea borrowed from cortinico/kotlin-android-template).
# Run ONCE right after creating a repo from this template:
#
#   scripts/customizer.sh <package-name> <AppName> [repo-name]
#
# Example:
#   scripts/customizer.sh io.github.ranzlappen.hardwaretool HardwareTool hardware-tool
#
# It rewrites the package, app name, and repository references, moves the
# source trees, and regenerates the public fallback keystore so the new app
# has its own (still deliberately public) signature.
#
# After running it, work through REPO_SETUP_CHECKLIST.md and let your AI
# agent run the LASTENHEFT.md final pass.

set -euo pipefail

OLD_PACKAGE="io.github.ranzlappen.template"
OLD_APP_NAME="Template"
OLD_REPO="Ranzlappen/Kotlin"

if [ "$#" -lt 2 ]; then
  echo "Usage: $0 <package-name> <AppName> [repo-name]" >&2
  echo "Example: $0 io.github.ranzlappen.myapp MyApp my-app" >&2
  exit 1
fi

NEW_PACKAGE="$1"
NEW_APP_NAME="$2"
NEW_REPO="${3:-$NEW_APP_NAME}"
OWNER="$(dirname "$OLD_REPO")"

if ! echo "$NEW_PACKAGE" | grep -Eq '^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)+$'; then
  echo "Error: '$NEW_PACKAGE' is not a valid package name." >&2
  exit 1
fi

cd "$(dirname "$0")/.."

OLD_PATH="${OLD_PACKAGE//.//}"
NEW_PATH="${NEW_PACKAGE//.//}"

echo "Rewriting package $OLD_PACKAGE -> $NEW_PACKAGE"
grep -rl --exclude-dir=.git --exclude-dir=build --exclude="*.jar" --exclude="*.keystore" "$OLD_PACKAGE" . \
  | while read -r file; do
      sed -i.bak "s/${OLD_PACKAGE//./\\.}/$NEW_PACKAGE/g" "$file" && rm "$file.bak"
    done

echo "Moving source trees"
find . -path ./.git -prune -o -type d -path "*/src/*/kotlin/$OLD_PATH" -print -o -type d -path "*/src/*/java/$OLD_PATH" -print \
  | while read -r dir; do
      base="${dir%"/$OLD_PATH"}"
      mkdir -p "$base/$(dirname "$NEW_PATH")"
      mv "$dir" "$base/$NEW_PATH"
    done
# Clean up now-empty old package directories.
find . -path ./.git -prune -o -type d -empty -print | while read -r d; do rmdir "$d" 2>/dev/null || true; done

echo "Renaming app to $NEW_APP_NAME"
sed -i.bak "s/rootProject.name = \"KotlinTemplate\"/rootProject.name = \"$NEW_APP_NAME\"/" settings.gradle.kts && rm settings.gradle.kts.bak
sed -i.bak "s/<string name=\"app_name\" translatable=\"false\">$OLD_APP_NAME<\/string>/<string name=\"app_name\" translatable=\"false\">$NEW_APP_NAME<\/string>/" app/src/main/res/values/strings.xml && rm app/src/main/res/values/strings.xml.bak

echo "Rewriting repository references $OLD_REPO -> $OWNER/$NEW_REPO"
grep -rl --exclude-dir=.git --exclude-dir=build --exclude="*.jar" --exclude="*.keystore" --exclude="customizer.sh" "$OLD_REPO" . \
  | while read -r file; do
      sed -i.bak "s#$OLD_REPO#$OWNER/$NEW_REPO#g" "$file" && rm "$file.bak"
    done

if command -v keytool >/dev/null 2>&1; then
  echo "Regenerating public fallback keystore"
  rm -f signing/fallback.keystore
  keytool -genkeypair -keystore signing/fallback.keystore \
    -storepass template -keypass template -alias template \
    -keyalg RSA -keysize 2048 -validity 10000 \
    -dname "CN=$NEW_APP_NAME Fallback, OU=Public Fallback Key, O=$OWNER" >/dev/null 2>&1
else
  echo "WARNING: keytool not found; keeping the template's fallback keystore." >&2
fi

echo
echo "Done. Next steps:"
echo "  1. Review the diff, then commit."
echo "  2. Work through REPO_SETUP_CHECKLIST.md (repo admin settings)."
echo "  3. Have your AI agent run the LASTENHEFT.md final pass."
echo "  4. Optionally delete scripts/customizer.sh."
