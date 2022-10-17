import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class ScriptCosmosIssues {

	private static final String ID_REG = "id_reg";
	private static final String NAME_VAL = "name_val";
	private static final String DESCR_VAL = "descr_val";
	private static final String OBS_VAL = "obs_val";
	private static final String TYPE_VAL = "type_val";
	private static final String STATUS_VAL = "status_val";
	private static final String ASSIGNED_TO_VAL = "assigned_to_val";
	private static final String CONCEPT_VAL = "concept_val";
	private static final String CONFIDENCE_VAL = "confidence_val";
	private static final String OPERATION_VAL = "operation_val";
	private static final String LAST_UPDATED_VAL = "last_updated_val";

	private static final String[] CONCEPTS_VALUES = {"blockbuster.actor", "blockbuster.director", "blockbuster.film"};
	private static final String[] NAME_VALUES = {"BLOACT", "BLODIR", "BLOFIL"};
	private static final String[] STATUSES_VALUES = {"ACTIVE", "SOLVED", "DISCARDED"};
	private static final String[] TYPES_VALUES = {"MERGE", "DUPLICATE_DETECTION"};
	private static final String[] USERS_VALUES = {"m.perez", "a.garcia", "h.molina"};

	private static final String[] DATES_VALUES = {"2022-01-day hour:min:27", "2022-02-day hour:min:02", "2022-03-day hour:min:51",
			"2022-04-day hour:min:00", "2022-05-day hour:min:33", "2022-06-day hour:min:34",
			"2022-07-day hour:min:26", "2022-08-day hour:min:41", "2022-09-day hour:min:05", "2022-10-day hour:min:25"};

	private static final String[] OPERATION_VALUES = {"{\"status\":[\"status_val\"],\"version\":\"1.0\",\"conceptId\":\"blockbuster.actor\",\"threshold\":\"confidence_val\",\"issue_type\":\"type_val\",\"rows_affected\":[{\"gid\":\"00d967c124b74d44cddbdc347f72c0156b5ee728ce1d48dd9f862283c619839f\"},{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"},{\"gid\":\"0479c57122eeb89683aa2535fc79561f33e0b4ddcecf654323b9d931c19166b9\"}],\"merge_proposal\":{\"dni\":{\"gid\":\"00d967c124b74d44cddbdc347f72c0156b5ee728ce1d48dd9f862283c619839f\"},\"last_name\":{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"},\"first_name\":{\"gid\":\"0479c57122eeb89683aa2535fc79561f33e0b4ddcecf654323b9d931c19166b9\"},\"phone_number\":{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"}},\"primary_keys_properties\":[\"first_name\",\"last_name\"]}",
			"{\"status\":[\"status_val\"],\"version\":\"1.0\",\"conceptId\":\"blockbuster.director\",\"threshold\":\"confidence_val\",\"issue_type\":\"type_val\",\"rows_affected\":[{\"gid\":\"00d967c124b74d44cddbdc347f72c0156b5ee728ce1d48dd9f862283c619839f\"},{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"},{\"gid\":\"0479c57122eeb89683aa2535fc79561f33e0b4ddcecf654323b9d931c19166b9\"}],\"merge_proposal\":{\"dni\":{\"gid\":\"00d967c124b74d44cddbdc347f72c0156b5ee728ce1d48dd9f862283c619839f\"},\"last_name\":{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"},\"first_name\":{\"gid\":\"0479c57122eeb89683aa2535fc79561f33e0b4ddcecf654323b9d931c19166b9\"},\"phone_number\":{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"}},\"primary_keys_properties\":[\"first_name\",\"last_name\"]}",
			"{\"status\":[\"status_val\"],\"version\":\"1.0\",\"conceptId\":\"blockbuster.film\",\"threshold\":\"confidence_val\",\"issue_type\":\"type_val\",\"rows_affected\":[{\"gid\":\"00d967c124b74d44cddbdc347f72c0156b5ee728ce1d48dd9f862283c619839f\"},{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"},{\"gid\":\"0479c57122eeb89683aa2535fc79561f33e0b4ddcecf654323b9d931c19166b9\"}],\"merge_proposal\":{\"category\":{\"gid\":\"00d967c124b74d44cddbdc347f72c0156b5ee728ce1d48dd9f862283c619839f\"},\"director\":{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"},\"title\":{\"gid\":\"0479c57122eeb89683aa2535fc79561f33e0b4ddcecf654323b9d931c19166b9\"},\"year\":{\"gid\":\"015a566b18c6d2125e5f66f04eda003c2ccc1bfc776d660d3f6d512eeae61d3d\"}},\"primary_keys_properties\":[\"title\",\"year\"]}"
	};

	private static final String HEADER = "INSERT INTO ISSUE_MANAGER.MDM_ISSUES (id, name,description,observations,type,category,status,created_by,assigned_to,concept_id,confidence,operation,discard_code_reason,discard_observations,last_updated,audit,logical_delete) VALUES\n";

	private static final String BASE_VALUES = "(id_reg, 'name_val', 'descr_val', 'obs_val', 'type_val', 'GOLDEN_RECORD', 'status_val', 'creatoruser' , 'assigned_to_val', 'concept_val', confidence_val, operation_val, NULL, NULL, 'last_updated_val' , NULL, false),\n";

	public static void main (String [ ] args) {

		String result = HEADER;
		String aux = BASE_VALUES;

		Random r = new Random();

		double confidence;
		int statusDecision;
		int userDecision;
		int conceptAndNameDecision;
		int typeDecision;
		int dateDecision;
		int randomInsertOperation;

		String name;
		String user;
		String concept;
		String type;
		String status;

		String day;
		String hour;
		String minutes;

		String date;

		String operation;

		for (int id_reg = 1; id_reg < 100; id_reg++) {

			confidence = (r.nextInt(100) * 1.0) + (r.nextInt(9) * 0.1);
			statusDecision = r.nextInt(3);
			userDecision = r.nextInt(3);
			conceptAndNameDecision = r.nextInt(3);
			typeDecision = r.nextInt(2);
			dateDecision = id_reg / 10;
			randomInsertOperation = r.nextInt(30);

			name = NAME_VALUES[conceptAndNameDecision];
			user = USERS_VALUES[userDecision];
			concept = CONCEPTS_VALUES[conceptAndNameDecision];
			type = TYPES_VALUES[typeDecision];
			status = STATUSES_VALUES[statusDecision];

			day = "" + (r.nextInt(27) + 1);
			if(day.length() == 1){
				day = "0" + day;
			}

			hour = "" + r.nextInt(23);
			if(hour.length() == 1){
				hour = "0" + hour;
			}

			minutes = "" + r.nextInt(59);
			if(minutes.length() == 1){
				minutes = "0" + minutes;
			}

			date = DATES_VALUES[dateDecision].replace("day", day).replace("hour", hour).replace("min", minutes);

			if((status.equals("ACTIVE") && randomInsertOperation > 20) || status.equals("DISCARDED"))
				operation = "NULL";
			else
				operation = "'" + OPERATION_VALUES[conceptAndNameDecision].replace(STATUS_VAL, status).replace(CONFIDENCE_VAL, confidence+"").replace(TYPE_VAL, type) + "'";

			System.out.println("Doing reg " + id_reg + " ...");

			aux = aux.replace(ID_REG, ""+id_reg).replace(NAME_VAL, name + "-" + id_reg)
					.replace(DESCR_VAL, "description value -- " + name )
					.replace(OBS_VAL, "observations_val -- " + name)
					.replace(TYPE_VAL, type).replace(STATUS_VAL, status)
					.replace(ASSIGNED_TO_VAL, user).replace(CONCEPT_VAL, concept)
					.replace(CONFIDENCE_VAL, ""+confidence)
					.replace(OPERATION_VAL, operation).replace(LAST_UPDATED_VAL, date);

			result = result + aux;

			aux = BASE_VALUES;
		}

		confidence = (r.nextInt(100) * 1.0) + (r.nextInt(9) * 0.1);
		statusDecision = r.nextInt(3);
		userDecision = r.nextInt(3);
		conceptAndNameDecision = r.nextInt(3);
		typeDecision = r.nextInt(2);

		name = NAME_VALUES[conceptAndNameDecision];
		user = USERS_VALUES[userDecision];
		concept = CONCEPTS_VALUES[conceptAndNameDecision];
		type = TYPES_VALUES[typeDecision];
		status = STATUSES_VALUES[statusDecision];

		day = "" + (r.nextInt(27) + 1);
		if(day.length() == 1){
			day = "0" + day;
		}

		hour = "" + r.nextInt(23);
		if(hour.length() == 1){
			hour = "0" + hour;
		}

		minutes = "" + r.nextInt(59);
		if(minutes.length() == 1){
			minutes = "0" + minutes;
		}

		date = DATES_VALUES[9].replace("day", day).replace("hour", hour).replace("min", minutes);

		if(!status.equals("DISCARDED"))
			operation = "'" + OPERATION_VALUES[conceptAndNameDecision].replace(STATUS_VAL, status).replace(CONFIDENCE_VAL, confidence+"").replace(TYPE_VAL, type) + "'";
		else
			operation = "NULL";

		aux = aux.replace(ID_REG, "100").replace(NAME_VAL, name + "-100" )
				.replace(DESCR_VAL, "description value -- " + name )
				.replace(OBS_VAL, "observations_val -- " + name)
				.replace(TYPE_VAL, type).replace(STATUS_VAL, status)
				.replace(ASSIGNED_TO_VAL, user).replace(CONCEPT_VAL, concept)
				.replace(CONFIDENCE_VAL, ""+confidence)
				.replace(OPERATION_VAL, operation)
				.replace(LAST_UPDATED_VAL, date)
				.replace("),", ");");

		result = result + aux;


		Date d= new Date();
		String filename = "script-cosmos-issues-" + d.getTime() + ".sql";
		try {
			File file = new File("/home/msierra/" + filename);

			file.createNewFile();

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("File generated at /home/msierra with name " + filename);
	}

}

