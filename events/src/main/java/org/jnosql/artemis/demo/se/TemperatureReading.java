package org.jnosql.artemis.demo.se;

/**
 * A Temperature Reading from a Temperature Sensor.
 */
public class TemperatureReading {

	/**
	 * Device Unique ID
	 */
	private String deviceId;

	/**
	 * Reading timestamp (since epoch, in ms). Tracks when it was read by the
	 * sensor, synched using GPS.
	 */
	private long timestamp;

	/**
	 * Temperature in Celsius
	 */
	private float temperature;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "TemperatureReading [deviceId=" + deviceId + ", timestamp=" + timestamp + ", temperature=" + temperature
				+ "]";
	}

}
