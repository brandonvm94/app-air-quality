class Details {
    constructor(sensorId, date, temperature, humidity, no2, o3, no, so2, pm1, pm25, pm10, co, h2s, ambientTemperature, ambientHumidity, ambientPressure){
        this.sensorId = sensorId;
        this.date = date;
        this.temperature = temperature; 
        this.humidity = humidity;
        this.no2 = no2;
        this.o3 = o3;
        this.no = no;
        this.so2 = so2;
        this.pm1 = pm1;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.co = co;
        this.h2s = h2s;
        this.ambientTemperature = ambientTemperature;
        this.ambientHumidity = ambientHumidity;
        this.ambientPressure = ambientPressure;
    }
}
module.exports = Details;