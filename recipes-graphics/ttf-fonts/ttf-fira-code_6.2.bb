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

SRC_URI = "https://github.com/tonsky/FiraCode/releases/download/${PV}/Fira_Code_v${PV}.zip"
SRC_URI[md5sum] = "77dfd6d902db1ee8d8a6722373ce7933"
SRC_URI[sha256sum] = "0949915ba8eb24d89fd93d10a7ff623f42830d7c5ffc3ecbf960e4ecad3e3e79"

S = "${WORKDIR}"

FILES:${PN} = "${datadir}/fonts/truetype/*.ttf"