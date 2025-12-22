require maplibre-native-qt_3.0.0.inc

inherit cmake_qt5 

EXTRA_OECMAKE:append = ' \
    -DQT_VERSION_MAJOR=5 \
'

FILES:${PN} += " \    
    ${OE_QMAKE_PATH_PLUGINS}/geoservices/libqtgeoservices_maplibre.so \
    ${OE_QMAKE_PATH_QML}/MapLibre/qmldir \
    ${OE_QMAKE_PATH_QML}/MapLibre/libdeclarative_locationplugin_maplibre.so \
"
