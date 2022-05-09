DESCRIPTION ?= "TrueType font package ${PN}"
SECTION = "fonts"

INHIBIT_DEFAULT_DEPS = "1"

do_install() {
    install -d ${D}${datadir}/fonts/truetype/
    find ./ -name '*.tt[cf]' -exec install -m 0644 {} ${D}${datadir}/fonts/truetype/ \;
}

inherit allarch fontcache

SUMMARY = "Fira code font"
HOMEPAGE = "https://github.com/tonsky/FiraCode"
LICENSE = "OFL-1.1"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=fce5baa9b16328f04e2afc29f6e4e882"

SRC_URI = "https://github.com/tonsky/FiraCode/releases/download/${PV}/Fira_Code_v${PV}.zip"
SRC_URI[md5sum] = "3b43a5cb33196ec25e44d5fcb40219e1"
SRC_URI[sha256sum] = "c825453253f590cfe62557733e7173f9a421fff103b00f57d33c4ad28ae53baf"

S = "${WORKDIR}"

FILES:${PN} = "${datadir}/fonts/truetype/*.ttf"