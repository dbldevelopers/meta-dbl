SUMMARY = "Cross-platform and OpenSource \"actor framework\" for C++"
DESCRIPTION = "SObjectizer is one of a few cross-platform and OpenSource \
\"actor frameworks\" for C++. But SObjectizer supports not only Actor Model, \
but also Publish-Subscribe Model and CSP-like channels. \
The goal of SObjectizer is significant simplification of development of \
concurrent and multithreaded applications in C++."
HOMEPAGE = "https://github.com/Stiffstream/sobjectizer"
LICENSE = "sobjectizer"
LIC_FILES_CHKSUM = "file://${WORKDIR}/so-${PV}/LICENSE;md5=99697bb5d090fdcf8f6efe75797b019a"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI = " \
    https://github.com/Stiffstream/sobjectizer/releases/download/v.${PV}/so-${PV}.tar.gz \
    file://0001-pkg-config-support.patch;patchdir=${WORKDIR}/so-${PV} \
"
UPSTREAM_CHECK_URI = "https://github.com/Stiffstream/sobjectizer/releases"

inherit cmake pkgconfig

S = "${WORKDIR}/so-${PV}/dev"

SRC_URI[md5sum] = "3f43376894cca4fae16de01ff1286548"
SRC_URI[sha256sum] = "897dab84c922ee4f62baf11aa31b88eeff05ca6bb070eb2a03ce9c1a42b9b10d"

FILES:${PN} = " \
    ${libdir}/*.so* \
"

FILES:${PN}-dev = " \
    ${includedir}/so_5/* \
    ${libdir}/pkgconfig/sobjectizer.pc \
    ${libdir}/pkgconfig/sobjectizer-static.pc \
    ${libdir}/cmake/sobjectizer/*.cmake \
    ${libdir}/*.a \
"
