DESCRIPTION ?= "TrueType font package ${PN}"
SECTION = "fonts"

INHIBIT_DEFAULT_DEPS = "1"

do_install() {
    install -d ${D}${datadir}/fonts/truetype/
    find ./ -name '*.tt[cf]' -exec install -m 0644 {} ${D}${datadir}/fonts/truetype/ \;
}

inherit allarch fontcache

SUMMARY = "JetBrains Mono font"
HOMEPAGE = "https://www.jetbrains.com/lp/mono/"
LICENSE = "OFL-1.1"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/OFL-1.1;md5=fac3a519e5e9eb96316656e0ca4f2b90"

SRC_URI = "https://github.com/JetBrains/JetBrainsMono/releases/download/v${PV}/JetBrainsMono-${PV}.zip"
SRC_URI[md5sum] = "ce7fe9233700251c34e409dc85ef91d4"
SRC_URI[sha256sum] = "4e315b4ef176ce7ffc971b14997bdc8f646e3d1e5b913d1ecba3a3b10b4a1a9f"

S = "${WORKDIR}"

FILES:${PN} = "${datadir}/fonts/truetype/*.ttf"