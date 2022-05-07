SUMMARY = "Noto fonts - TTF Version"
DESCRIPTION ?= "TrueType font package ${PN}"
SECTION = "fonts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
PR = "r0"

INHIBIT_DEFAULT_DEPS = "1"

inherit allarch fontcache 

FONT_PACKAGES = "${PN}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://Noto_Sans_Arabic.zip \
"

do_install() {
    install -m 0755 -d ${D}${datadir}/fonts/truetype/Noto
    install -m 0644 ${WORKDIR}/*.ttf ${D}${datadir}/fonts/truetype/Noto
}

FILES:${PN} += "${datadir}/fonts/truetype/Noto/*.ttf"
