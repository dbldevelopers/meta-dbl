SUMMARY = "GeographicLib C++ library"

DESCRIPTION = " \
GeographicLib is a small C++ library for \
- geodesic and rhumb line calculations; \
- conversions between geographic, UTM, UPS, MGRS, geocentric, and local cartesian coordinates; \
- gravity (e.g., EGM2008) and geomagnetic field (e.g., WMM2020) calculations."

SECTION = "system"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

inherit cmake pkgconfig

SRC_URI = "git://github.com/geographiclib/geographiclib;branch=release;protocol=https"  

PV = "2.2"
SRCREV = "f98b68dc425a797cf923a52388020ff05de8c1b1"

S = "${WORKDIR}/git"

EXTRA_OECMAKE:append = '-DCMAKE_BUILD_TYPE=Release'

INSANE_SKIP:${PN} += "dev-so"

SYSROOT_DIRS += "/usr/bin"
