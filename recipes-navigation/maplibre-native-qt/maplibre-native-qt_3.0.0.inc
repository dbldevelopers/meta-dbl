DESCRIPTION ?= "MapLibre Native Qt bindings and Qt Location MapLibre Plugin"
HOMEPAGE = "https://maplibre.org/"
LICENSE = "BSD-2-Clause & MIT & GPL-2.0-only & GPL-3.0-only & LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSES/BSD-2-Clause.txt;md5=272be00ca1ae12eceb040a3946c3c2cc"
SUMMARY = "MapLibre Native Qt bindings and Qt Location MapLibre Plugin"

FILESEXTRAPATHS:prepend := "${THISDIR}/maplibre-native-qt:"

inherit qtquickcompiler

SRC_URI = " \
    https://github.com/maplibre/maplibre-native-qt/releases/download/v${PV}/maplibre-native-qt_v${PV}_Source.tar.bz2 \
    file://0001_testing_as_option.patch \
    file://0002_location_plugins_oe_path.patch \
"

SRC_URI[md5sum] = "5d5bd971b98abe7df2317463d45e84dc"
SRC_URI[sha256sum] = "9689fb0630adc33a34ea476bdf53a3ef7dc76cb3c95da9de056081f9330a9355"

S = "${WORKDIR}/maplibre-native-qt_v${PV}_Source"

DEPENDS = " \
    qttools-native \
    qttools \
    qtbase \
    qtdeclarative \
    qtlocation \
    sqlite3 \
"

RDEPENDS:${PN} += " \
    qtbase \    
    qtdeclarative \
    qtlocation \
    libsqlite3 \
"

EXTRA_OECMAKE:append = ' \
    -DMLN_QT_WITH_WIDGETS=OFF \
    -DMLN_WITH_WERROR=OFF \
    -DBUILD_TESTING=OFF \
    -DMLN_QT_ENABLE_TESTING=OFF \
    -DMAPBOX_BASE_BUILD_TESTING=OFF \
    -DCMAKE_BUILD_TYPE=Release \
    -DMLN_QT_WITH_INTERNAL_ICU=ON \
'
