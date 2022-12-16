DESCRIPTION ?= "TrueType font package ${PN}"
SECTION = "fonts"

INHIBIT_DEFAULT_DEPS = "1"

do_install() {
    install -d ${D}${datadir}/fonts/truetype/
    find ./ -name '*.tt[cf]' -exec install -m 0644 {} ${D}${datadir}/fonts/truetype/ \;
}

inherit allarch fontcache

SUMMARY = "IBM Plex fonts"
HOMEPAGE = "https://github.com/IBM/plex"
LICENSE = "OFL-1.1"
LIC_FILES_CHKSUM = "file://${S}/TrueType/IBM-Plex-Mono/license.txt;md5=b5ee9b6babb1b6fa3ee8ed1c9f120990"

SRC_URI = "https://github.com/IBM/plex/releases/download/v${PV}/TrueType.zip"
SRC_URI[md5sum] = "80d2416485bc24d785bbf3be94129991"
SRC_URI[sha256sum] = "16253500de140321175bb94e65fd227c6b12efc050eb298c20322dbf3713a8e0"

S = "${WORKDIR}"

FILES:${PN} = "${datadir}/fonts/truetype/*.ttf"