require maplibre-native-qt_3.0.0.inc

inherit qt6-cmake

EXTRA_OECMAKE:append = ' \
    -DQT_VERSION_MAJOR=6 \
'

DEPENDS:append = " qtpositioning"
RDEPENDS:${PN} += " qtpositioning"

FILES:${PN} += " \    
    ${QT6_INSTALL_PLUGINSDIR}/geoservices/libqtgeoservices_maplibre.so \
    ${QT6_INSTALL_QMLDIR}/MapLibre/qmldir \
    ${QT6_INSTALL_QMLDIR}/MapLibre/libdeclarative_locationplugin_maplibre.so \
"
