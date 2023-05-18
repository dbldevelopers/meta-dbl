SUMMARY = "Cross-platform and OpenSource \"actor framework\" for C++"
DESCRIPTION = "SObjectizer is one of a few cross-platform and OpenSource     \
\"actor frameworks\" for C++. But SObjectizer supports not only Actor Model, \
but also Publish-Subscribe Model and CSP-like channels.                      \
The goal of SObjectizer is significant simplification of development of      \
concurrent and multithreaded applications in C++."
HOMEPAGE = "https://github.com/Stiffstream/sobjectizer"
LICENSE = "sobjectizer"
LIC_FILES_CHKSUM = "file://${WORKDIR}/so-${PV}/LICENSE;md5=5495a75740338998788ccdbe7a1499fc"

FILESEXTRAPATHS:prepend := "${THISDIR}/{BPN}:"
SRC_URI = "                                                                              \
    https://github.com/Stiffstream/sobjectizer/releases/download/v.${PV}/so-${PV}.tar.gz \
    file://0001-pkg-config-support.patch;patchdir=${WORKDIR}/so-${PV}                    \
"
UPSTREAM_CHECK_URI = "https://github.com/Stiffstream/sobjectizer/releases"

inherit cmake pkgconfig

S = "${WORKDIR}/so-${PV}/dev"

SRC_URI[md5sum] = "111c17ce68d21bab25058d6c3984a015"
SRC_URI[sha256sum] = "503982b18ba6bb60c5bd0b5bc705e8c3c050b3c28733f5f056f288f321b6fa36"

INSANE_SKIP:${PN} = "license-exists"

FILES:${PN} = "     \
    ${libdir}/*.so* \
"

FILES:${PN}-dev = "                           \
    ${includedir}/so_5/*                      \
    ${libdir}/pkgconfig/sobjectizer.pc        \
    ${libdir}/pkgconfig/sobjectizer-static.pc \
    ${libdir}/cmake/sobjectizer/*.cmake       \
    ${libdir}/*.a                             \
"
