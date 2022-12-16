SUMMARY = "Видеозахват GigE камер"
SECTION = "system"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://github.com/AravisProject/aravis;branch=main \
	file://0001-impx-io-fix.patch \
	file://0002-priv-iodata-release-fix.patch \
"

PV = "2022.05.03-0.8.20"
SRCREV = "6c57c65b55d467ffa9ef635162c5a60711289613"

inherit meson pkgconfig gettext 

DEPENDS = " \
	glib-2.0 \
	glib-2.0-native \
	intltool-native \
	libxml2 \
	gtk-doc \
	zlib \
"

RDEPENDS:${PN} = " \
	zlib \
"

S = "${WORKDIR}/git"

INSANE_SKIP:${PN} = "dev-so"

FILES:${PN} = " \
	${libdir}/gstreamer-1.0/*.so \
	${libdir}/*.so* \
	${bindir}/arv-* \
	${datadir}/aravis-0.8/arv-fake-camera.xml \
	${datadir}/locale/* \
"

FILES:${PN}-dev = " \
	${includedir}/aravis-0.8/*.h \
	${libdir}/pkgconfig/aravis-0.8.pc \
	${libdir}/gstreamer-1.0/*.la \
"

FILES:${PN}-staticdev = "${libdir}/gstreamer-1.0/*.a"

FILES:${PN}-dbg = " \
	${libdir}/gstreamer-1.0/.debug \
	${libdir}/.debug \
	${bindir}/.debug \
"

EXTRA_OEMESON += "-Dtests=false \
				  -Dviewer=disabled \
				  -Dusb=disabled \
				  -Ddocumentation=disabled \
				  -Dintrospection=disabled \
				  -Dfast-heartbeat=false \
"
