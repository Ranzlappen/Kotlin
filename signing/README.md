# Signing

## `fallback.keystore` — intentionally public

This keystore (store/key password `template`, alias `template`) is
**committed on purpose and provides zero security**. It exists so that:

- debug builds from every machine and every CI run share one signature
  (in-place updates keep working), and
- releases cut before real signing secrets exist still ship an installable
  release APK/AAB.

Never treat it as a secret. When adopting the template,
`scripts/customizer.sh` regenerates it so your app gets its own (still
public) fallback identity.

## Real release signing

Configure these repository **secrets** (Settings → Secrets and variables →
Actions); the release workflow picks them up automatically:

| Secret | Purpose |
| --- | --- |
| `KEYSTORE_BASE64` | Base64 of your `release.keystore` (`base64 -w0 release.keystore`) |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_ALIAS` | Signing key alias |
| `KEY_PASSWORD` | Signing key password |

Local release signing: put a `keystore.properties` (gitignored) at the repo
root with `storeFile`, `storePassword`, `keyAlias`, `keyPassword`.

Once real secrets exist, never fall back to the public keystore for
production releases — the signatures differ, so users would have to
uninstall/reinstall.
