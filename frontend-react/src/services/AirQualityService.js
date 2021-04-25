import baseURL from "./../config/baseURL";

const CITY_AIR_QUALITY_URL = baseURL + "airquality";

class AirQualityService {
    async getCityAirQuality(city, country=null) {
        var res = null;
        if (country) {
            res = await fetch(
                CITY_AIR_QUALITY_URL + '?city=' + city + '&country=' + country, {
                method: 'GET',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            console.log(CITY_AIR_QUALITY_URL + '?city=' + city + '&country=' + country)
        } else {
            res = await fetch(
                CITY_AIR_QUALITY_URL + '?city=' + city, {
                method: 'GET',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            console.log(CITY_AIR_QUALITY_URL + '?city=' + city)
        }
        console.log(res)
        return await res.json();
    }
}
export default new AirQualityService();