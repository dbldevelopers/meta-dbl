#include <QtQml/QtQml>

#include <QObject>
#include <QtGlobal>
#include <QMetaType>
#include <QQmlEngine>

#include <osmscout/OSMScoutQt.h>
#include <osmscout/Settings.h>
#include <osmscout/DBThread.h>
#include <osmscout/DataTileCache.h>
#include <osmscout/MapWidget.h>
#include <osmscout/ElevationChartWidget.h>
#include <osmscout/PlaneMapRenderer.h>
#include <osmscout/TiledMapRenderer.h>
#include <osmscout/OverlayObject.h>
#include <osmscout/util/Distance.h>

#include <osmscout/AvailableMapsModel.h>
#include <osmscout/LocationInfoModel.h>
#include <osmscout/MapDownloadsModel.h>
#include <osmscout/MapObjectInfoModel.h>
#include <osmscout/MapStyleModel.h>
#include <osmscout/OnlineTileProviderModel.h>
#include <osmscout/RoutingModel.h>
#include <osmscout/SearchLocationModel.h>
#include <osmscout/StyleFlagsModel.h>
#include <osmscout/TiledMapOverlay.h>
#include <osmscout/Router.h>
#include <osmscout/NavigationModel.h>
#include <osmscout/NearPOIModel.h>
#include <osmscout/InstalledMapsModel.h>
#include <osmscout/AvailableVoicesModel.h>
#include <osmscout/InstalledVoicesModel.h>
#include <osmscout/QmlRoutingProfile.h>

#include <QtQml/QQmlExtensionPlugin>

QT_BEGIN_NAMESPACE

class OsmScoutQmlPlugin: public QQmlExtensionPlugin
{
    Q_OBJECT
    Q_PLUGIN_METADATA(IID QQmlExtensionInterface_iid)

public:
    void registerTypes(const char* uri) override
    {
        qRegisterMetaType<osmscout::DatabaseLoadedResponse>("osmscout::DatabaseLoadedResponse");
        qRegisterMetaType<osmscout::LocationEntryRef>("osmscout::LocationEntryRef");
        qRegisterMetaType<osmscout::BreakerRef>("osmscout::BreakerRef");
        qRegisterMetaType<osmscout::Distance>("osmscout::Distance");
        qRegisterMetaType<osmscout::Bearing>("osmscout::Bearing");
        qRegisterMetaType<std::shared_ptr<osmscout::Bearing>>("std::shared_ptr<osmscout::Bearing>");
        qRegisterMetaType<std::optional<osmscout::Bearing>>("std::optional<osmscout::Bearing>");
        qRegisterMetaType<osmscout::GeoBox>("osmscout::GeoBox");
        qRegisterMetaType<osmscout::GeoCoord>("osmscout::GeoCoord");
        qRegisterMetaType<osmscout::LocationDescription>("osmscout::LocationDescription");
        qRegisterMetaType<osmscout::MapData>("osmscout::MapData");
        qRegisterMetaType<osmscout::TileRef>("osmscout::TileRef");
        qRegisterMetaType<osmscout::Vehicle>("osmscout::Vehicle");
        qRegisterMetaType<osmscout::PositionAgent::PositionState>("osmscout::PositionAgent::PositionState");
        qRegisterMetaType<osmscout::LaneAgent::Lane>("osmscout::LaneAgent::Lane");
        qRegisterMetaType<QList<osmscout::LocationEntry>>("QList<osmscout::LocationEntry>");
        qRegisterMetaType<osmscout::MapViewStruct>("osmscout::MapViewStruct");
        qRegisterMetaType<osmscout::QtRouteData>("osmscout::QtRouteData");
        qRegisterMetaType<uint32_t>("uint32_t");
        qRegisterMetaType<uint64_t>("uint64_t");
        qRegisterMetaType<osmscout::AdminRegionInfoRef>("osmscout::AdminRegionInfoRef");
        qRegisterMetaType<QList<osmscout::AdminRegionInfoRef>>("QList<osmscout::AdminRegionInfoRef>");
        qRegisterMetaType<std::unordered_map<std::string,bool>>("std::unordered_map<std::string,bool>");
        qRegisterMetaType<QMap<QString,bool>>("QMap<QString,bool>");
        qRegisterMetaType<osmscout::LocationEntry>("osmscout::LocationEntry");
        qRegisterMetaType<osmscout::OnlineTileProvider>("osmscout::OnlineTileProvider");
        qRegisterMetaType<osmscout::RouteStep>("osmscout::RouteStep");
        qRegisterMetaType<std::list<osmscout::RouteStep>>("std::list<osmscout::RouteStep>");
        qRegisterMetaType<osmscout::OverlayWay*>("osmscout::OverlayWay*");
        qRegisterMetaType<std::shared_ptr<osmscout::OverlayWay>>("std::shared_ptr<osmscout::OverlayWay>");
        qRegisterMetaType<osmscout::OverlayArea*>("osmscout::OverlayArea*");
        qRegisterMetaType<osmscout::OverlayNode*>("osmscout::OverlayNode*");
        qRegisterMetaType<QList<osmscout::LookupModule::ObjectInfo>>("QList<osmscout::LookupModule::ObjectInfo>");
        qRegisterMetaType<osmscout::ElevationModule::ElevationPoints>("osmscout::ElevationModule::ElevationPoints");
        qRegisterMetaType<QList<QDir>>("QList<QDir>");

        // register osmscout types for usage in QML
        const int versionMajor = 1;
        const int versionMinor = 0;

        qmlRegisterType<osmscout::AvailableMapsModel>(uri, versionMajor, versionMinor, "AvailableMapsModel");
        qmlRegisterType<osmscout::LocationEntry>(uri, versionMajor, versionMinor, "LocationEntry");
        qmlRegisterType<osmscout::LocationInfoModel>(uri, versionMajor, versionMinor, "LocationInfoModel");
        qmlRegisterType<osmscout::LocationListModel>(uri, versionMajor, versionMinor, "LocationListModel");
        qmlRegisterType<osmscout::MapDownloadsModel>(uri, versionMajor, versionMinor, "MapDownloadsModel");
        qmlRegisterType<osmscout::MapObjectInfoModel>(uri, versionMajor, versionMinor, "MapObjectInfoModel");
        qmlRegisterType<osmscout::MapStyleModel>(uri, versionMajor, versionMinor, "MapStyleModel");
        qmlRegisterType<osmscout::MapWidget>(uri, versionMajor, versionMinor, "Map");
        qmlRegisterType<osmscout::ElevationChartWidget>(uri, versionMajor, versionMinor, "ElevationChart");
        qmlRegisterType<osmscout::NavigationModel>(uri, versionMajor, versionMinor, "NavigationModel");
        qmlRegisterType<osmscout::OnlineTileProviderModel>(uri, versionMajor, versionMinor, "OnlineTileProviderModel");
        qmlRegisterType<osmscout::OverlayWay>(uri, versionMajor, versionMinor, "OverlayWay");
        qmlRegisterType<osmscout::OverlayArea>(uri, versionMajor, versionMinor, "OverlayArea");
        qmlRegisterType<osmscout::OverlayNode>(uri, versionMajor, versionMinor, "OverlayNode");
        qmlRegisterType<osmscout::QmlSettings>(uri, versionMajor, versionMinor, "Settings");
        qmlRegisterType<osmscout::QmlRoutingProfile>(uri, versionMajor, versionMinor, "RoutingProfile");
        qmlRegisterType<osmscout::RouteStep>(uri, versionMajor, versionMinor, "RouteStep");
        qmlRegisterType<osmscout::RoutingListModel>(uri, versionMajor, versionMinor, "RoutingListModel");
        qmlRegisterType<osmscout::StyleFlagsModel>(uri, versionMajor, versionMinor, "StyleFlagsModel");
        qmlRegisterType<osmscout::TiledMapOverlay>(uri, versionMajor, versionMinor, "TiledMapOverlay");
        qmlRegisterType<osmscout::NearPOIModel>(uri, versionMajor, versionMinor, "NearPOIModel");
        qmlRegisterType<osmscout::InstalledMapsModel>(uri, versionMajor, versionMinor, "InstalledMapsModel");
        qmlRegisterType<osmscout::AvailableVoicesModel>(uri, versionMajor, versionMinor, "AvailableVoicesModel");
        qmlRegisterType<osmscout::InstalledVoicesModel>(uri, versionMajor, versionMinor, "InstalledVoicesModel");

        osmscout::OSMScoutQtBuilder builder = osmscout::OSMScoutQt::NewInstance();

        QStringList mapLookupDirectories;
        mapLookupDirectories << "/storage/maps";

        builder
        .WithStyleSheetDirectory("/usr/share/stylesheets")
        .WithStyleSheetFile("standard.oss")
        .WithIconDirectory("/usr/share/stylesheets/icons")
        .WithMapLookupDirectories(mapLookupDirectories)
        /*.WithOnlineTileProviders(":/resources/online-tile-providers.json")
           .WithMapProviders(":/resources/map-providers.json")
           .WithVoiceProviders(":/resources/voice-providers.json")
           .WithUserAgent("OSMScout2DemoApp", "v?")*/;

        if (!builder.Init() )
        {
            std::cerr << "Cannot initialize OSMScout library" << std::endl;
        }
    }

public:
    OsmScoutQmlPlugin(QObject *parent = 0) : QQmlExtensionPlugin(parent)
    {
    }

    ~OsmScoutQmlPlugin() override
    {
        osmscout::OSMScoutQt::FreeInstance();
    }
};

QT_END_NAMESPACE

#include "plugin.moc"
