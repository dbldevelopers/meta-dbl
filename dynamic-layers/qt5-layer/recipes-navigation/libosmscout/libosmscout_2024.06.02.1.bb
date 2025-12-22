SUMMARY = "OSM SCOUT LIBRARY"
DESCRIPTION = ""
SECTION = "system"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}:"

inherit cmake_qt5 pkgconfig

SRC_URI = " \
    https://github.com/Framstag/libosmscout/releases/download/v${PV}/libosmscout-${PV}.tar.xz \    
    file://0001-map-widget-correction.patch \
    file://0002-osmscout-style.patch \
    file://0003-glyph-draw-fix.patch \
    file://0004-tile-cache-dir-size.patch \
    file://0005-map-renderer-debug-messages-suppress.patch \
    file://icons/ \
"
SRC_URI[sha256sum] = "6dc4929e24fac27b4a24683599ec46b0faedd77251729a11bd098f670fcaec7e"

INSANE_SKIP:${PN} = "license-exists"

PV = "2024.06.02.1"

S = "${WORKDIR}/${BP}"

def depends_for_static_libs_config(d):
    if d.getVar('USE_STATIC_LIBS'):
        return ' '.join(sorted(["qtdeclarative-native"]))
    else:
        return ' '.join(sorted(["qtdeclarative"]))

def runtime_depends_for_static_libs_config(d):
    if d.getVar('USE_STATIC_LIBS'):
        return ''
    else:
        return ' '.join(sorted(["qtbase", \
                                "qtsvg", \
                                "qtlocation", \
                                "qtdeclarative", \
                                "qtimageformats", \
                                "qtmultimedia"]))

DEPENDS = " \
    qttools \
    qttools-native \
    qtbase \
    qtsvg \
    qtlocation \
    qtimageformats \
    qtmultimedia \
    ${@depends_for_static_libs_config(d)} \
    zlib \
    lzip \
    libxml2 \
    protobuf \
    protobuf-native \
"

RDEPENDS:${PN} += " \
    ${@runtime_depends_for_static_libs_config(d)} \
    zlib \
    lzip \
    libxml2 \
"

EXTRA_OECMAKE:append = ' \
    -DOSMSCOUT_BUILD_QT=ON \
    -DBUILD_SHARED_LIBS=ON \
    -DOSMSCOUT_BUILD_CLIENT_QT=ON \
    -DOSMSCOUT_BUILD_DEMOS=OFF \
    -DOSMSCOUT_BUILD_DOC_API=OFF \
    -DOSMSCOUT_BUILD_EXTERN_MATLAB=OFF \
    -DOSMSCOUT_BUILD_GPX=ON \
    -DOSMSCOUT_BUILD_IMPORT_TOOL_FOR_DISTRIBUTION=OFF \
    -DOSMSCOUT_BUILD_MAP_AGG=OFF \
    -DOSMSCOUT_BUILD_MAP_CAIRO=OFF \
    -DOSMSCOUT_BUILD_MAP_OPENGL=OFF \
    -DOSMSCOUT_BUILD_MAP_SVG=ON \
    -DOSMSCOUT_BUILD_OPENGL=OFF \
    -DOSMSCOUT_BUILD_SVG=OFF \
    -DOSMSCOUT_BUILD_WEBPAGE=OFF \
    -DOSMSCOUT_BUILD_WITH_OPENMP=ON \
    -DOSMSCOUT_DEBUG_COASTLINE=OFF \
    -DOSMSCOUT_DEBUG_GROUNDTILES=OFF \
    -DOSMSCOUT_DEBUG_LABEL_LAYOUTER=OFF \
    -DOSMSCOUT_DEBUG_ROUTING=OFF \
    -DOSMSCOUT_DEBUG_TILING=OFF \
    -DOSMSCOUT_ENABLE_SSE=ON \
    -DOSMSCOUT_BUILD_TESTS=OFF \
    -DOSMSCOUT_BUILD_TOOL_DUMPDATA=OFF \
    -DOSMSCOUT_BUILD_IMPORT=OFF \
    -DOSMSCOUT_BUILD_TOOL_IMPORT=OFF \
    -DOSMSCOUT_BUILD_OSMSCOUT2=OFF \
    -DOSMSCOUT_BUILD_TOOL_OSMSCOUT2=OFF \
    -DOSMSCOUT_BUILD_OSMSCOUTOPENGL=OFF \
    -DOSMSCOUT_BUILD_TOOL_OSMSCOUTOPENGL=OFF \
    -DOSMSCOUT_BUILD_PUBLICTRANSPORTMAP=OFF \
    -DOSMSCOUT_BUILD_TOOL_PUBLICTRANSPORTMAP=OFF \
    -DOSMSCOUT_BUILD_TOOL_STYLEEDITOR=OFF \
    -DOSMSCOUT_BUILD_STYLEEDITOR=OFF \
    -DCMAKE_VERBOSE_MAKEFILE=ON \
    -DCMAKE_BUILD_TYPE=Release \
'

do_install:append() {
    install -d ${D}${datadir}/osmscout/
    install -d ${D}${datadir}/osmscout/icons/
    install -m 0644 ${WORKDIR}/icons/* ${D}${datadir}/osmscout/icons/
}

do_patch:append() {
    bb.build.exec_func('remove_qt_keywords', d)
}

remove_qt_keywords() {
    find ${S}/libosmscout-client-qt/ -type f -name "*.h" -print0 | xargs -0 sed -i 's/signals/Q_SIGNALS/g'
    find ${S}/libosmscout-client-qt/ -type f -name "*.h" -print0 | xargs -0 sed -i 's/slots/Q_SLOTS/g'
    find ${S}/libosmscout-client-qt/ -type f -name "*.h" -print0 | xargs -0 sed -i 's/emit /Q_EMIT /g'
}

INSANE_SKIP:${PN} += "dev-so"

FILES:${PN} = " \
    ${bindir} \
    ${libdir}/*.so* \
    ${datadir}/osmscout/stylesheets/ \
    ${datadir}/osmscout/icons/ \
    ${datadir}/${PN} \
"

FILES:${PN}-dbg = " \ 
    ${bindir}/.debug \
    ${libdir}/.debug \
"

FILES:${PN}-dev = " \
    ${libdir}/pkgconfig/*.pc \
    ${includedir} \
    ${libdir}/cmake \
"

FILES:${PN}-staticdev = " \
    ${libdir}/*.a \
"
