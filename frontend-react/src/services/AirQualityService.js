import baseURL from "./../config/baseURL";

const CITY_AIR_QUALITY_URL = baseURL + "airquality";
const CACHE_AIR_QUALITY_URL = baseURL + "cachestats";
const CITIES = baseURL + "cities";

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
        } else {
            res = await fetch(
                CITY_AIR_QUALITY_URL + '?city=' + city, {
                method: 'GET',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
        }
        return await res.json();
    }

    async getCacheAirQuality() {
       const res = await fetch(
            CACHE_AIR_QUALITY_URL, {
            method: 'GET',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        return await res.json();
    }

    async getAllCities() {
        const res = await fetch(
             CITIES, {
             method: 'GET',
             mode: 'cors',
             headers: {
                 'Content-Type': 'application/json',
             }
         })
        return await res.json();
     }
}
export default new AirQualityService();