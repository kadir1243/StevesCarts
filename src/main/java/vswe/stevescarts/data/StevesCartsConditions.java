package vswe.stevescarts.data;

import java.util.Calendar;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import vswe.stevescarts.StevesCarts;

public class StevesCartsConditions {
	public static void init() {
		ResourceConditions.register(StevesCarts.id("date"), object -> {
			int month = object.get("month").getAsInt();
			int day = object.get("day").getAsInt();
			Calendar calendar = Calendar.getInstance();
			return calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) == day;
		});
	}
}
