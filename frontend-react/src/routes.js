import SearchAirQuality from "views/admin/SearchAirQuality.js";
import AirQuality from '@material-ui/icons/LocalHospitalRounded';
import CityAndStatistics from "views/admin/CityAndStatistics.js";
import EqualizerIcon from '@material-ui/icons/Equalizer';

var routes = [
  {
    path: "/search",
    name: "Air Quality",
    icon: AirQuality,
    iconColor: "Primary",
    component: SearchAirQuality,
    layout: "/admin",
  },
  {
    path: "/stats",
    name: "Cities and Statistics",
    icon: EqualizerIcon,
    iconColor: "Primary",
    component: CityAndStatistics,
    layout: "/admin",
  },
  {
    divider: true,
  },
];
export default routes;
