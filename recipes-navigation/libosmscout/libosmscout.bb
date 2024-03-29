SUMMARY = "OSM SCOUT LIBRARY"
DESCRIPTION = ""
SECTION = "system"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit cmake_qt5 pkgconfig

SRC_URI = " \
	git://github.com/Framstag/libosmscout;branch=master;protocol=https \
    file://0001-add-pkgconfig.patch \
    file://0002-glyph-draw-fix.patch \
    file://0003-qml-plugin.patch \
    file://0004-map-widget-draw.patch \
    file://0005-dbg-msg-remove.patch \
    file://0006-water-index-scale.patch \
    file://libosmscout.pc.in \
    file://plugin.cpp \
    file://plugins.qmltypes \
    file://qmldir \
    file://standard.oss \
    file://icons/ \
"  

PV = "1.1.1"
SRCREV = "fdc20dd4eb0865aa19e3e28de7a3959ad1d6dbda"

S = "${WORKDIR}/git"

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
                         -DOSMSCOUT_INSTALL_QML_PLUGIN=OFF \
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
                         -DOSMSCOUT_ENABLE_SSE=OFF \
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
    install -d ${D}${OE_QMAKE_PATH_QML}/net/sf/libosmscout/map/
    install -m 0644 ${WORKDIR}/plugins.qmltypes ${D}${OE_QMAKE_PATH_QML}/net/sf/libosmscout/map/
    install -m 0644 ${WORKDIR}/qmldir ${D}${OE_QMAKE_PATH_QML}/net/sf/libosmscout/map/
    ln -sf ${libdir}/libosmscout_client_qt.so.${PV} ${D}${OE_QMAKE_PATH_QML}/net/sf/libosmscout/map/libosmscout_client_qt.so
    
    install -d ${D}/data/maps/
    install -d ${D}${datadir}/stylesheets/
    install -d ${D}${datadir}/stylesheets/icons/
    install -m 0644 ${S}/stylesheets/* ${D}${datadir}/stylesheets/
    install -m 0644 ${WORKDIR}/standard.oss ${D}${datadir}/stylesheets/
    install -m 0644 ${WORKDIR}/icons/* ${D}${datadir}/stylesheets/icons/
}

do_patch:append() {
    bb.build.exec_func('fix_pkg_config', d)
    bb.build.exec_func('fix_qml_plugin_description', d)
}

fix_pkg_config() {
    cp ${WORKDIR}/libosmscout.pc.in ${S}/
}

fix_qml_plugin_description() {
    cp ${WORKDIR}/plugin.cpp ${S}/libosmscout-client-qt/src/osmscout/
}

INSANE_SKIP:${PN} += "dev-so"

FILES:${PN} = " \
	${bindir} \
	${libdir}/*.so* \
    /data/maps/ \
	${datadir}/stylesheets/ \
    ${datadir}/stylesheets/icons/ \
	${datadir}/${PN} \
    ${OE_QMAKE_PATH_QML}/net/sf/libosmscout/map/* \
"

FILES:${PN}-dbg = " \ 
	${bindir}/.debug \
	${libdir}/.debug \
"

FILES:${PN}-dev = " \
	${libdir}/pkgconfig/*.pc \
	${includedir} \
	${datadir}/cmake \
"

FILES:${PN}-staticdev = " \
    ${libdir}/*.a \
"
