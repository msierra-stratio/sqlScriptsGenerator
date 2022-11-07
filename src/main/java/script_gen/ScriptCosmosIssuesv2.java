package script_gen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class ScriptCosmosIssuesv2 {

	private static final String GID = "gid";
	private static final String PID_VALUE ="processId_value";
	private static final String ROWS_AFF_VAL ="rows_aff_val";

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

	private static final String CONCEPTS_VALUES = "blockbuster.actor";
	private static final String NAME_VALUES = "BLOACT";
	private static final String[] STATUSES_VALUES = {"ACTIVE", "SOLVED", "DISCARDED"};
	private static final String[] TYPES_VALUES = {"MERGE"};
	private static final String[] USERS_VALUES = {"m.perez", "a.garcia", "h.molina"};

	private static final String[] DATES_VALUES = {"2022-01-day hour:min:27", "2022-02-day hour:min:02", "2022-03-day hour:min:51",
			"2022-04-day hour:min:00", "2022-05-day hour:min:33", "2022-06-day hour:min:34",
			"2022-07-day hour:min:26", "2022-08-day hour:min:41", "2022-09-day hour:min:05", "2022-10-day hour:min:25"};

	private static final String OPERATION_VALUES = "{\"processId\":\"processId_value\",\"version\":\"1.0\",\"merge_proposal\":{\"last_name\":\"gid1\",\n" +
			"\"first_name\":\"gid2\"," +
			"\"phone1\":\"gid3\"," +
			"\"phone2\":\"gid4\"," +
			"\"company_name\":\"gid5\"," +
			"\"email\":\"gid6\"," +
			"\"web\":\"gid7\"," +
			"\"rating\":\"gid8\"," +
			"\"address\":\"gid9\"," +
			"\"post\":\"gid10\"," +
			"\"city\":\"gid11\"," +
			"\"state\":\"gid12\"," +
			"\"age\":\"gid13\"}}";


	private static final String HEADER = "INSERT INTO ISSUE_MANAGER.MDM_ISSUES (rows_affected, primary_keys_properties, name, description, observations, issue_type, category, " +
			"issue_status, created_by, assigned_to, concept_id, confidence, operation, discard_code_reason, discard_observations, last_updated, audit, logical_delete) VALUES\n";

	private static final String BASE_VALUES = "('{rows_aff_val}', '{\"first_name\", \"last_name\"}', 'name_val', 'descr_val', 'obs_val', 'type_val', 'GOLDEN_RECORDS', 'status_val', 'creatoruser' , 'assigned_to_val', 'concept_val', confidence_val, operation_val, NULL, NULL, 'last_updated_val' , NULL, false),\n";

	public static void main (String [ ] args) {

		Gson gson =new Gson();
		String gidsJson = "";

		try (BufferedReader br = new BufferedReader(new FileReader("/home/msierra/Documentos/git/sqlScriptsGenerator/src/main/resources/gids.json"))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				gidsJson += linea;
			}
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		Queue<Map<String,String>> gids = gson.fromJson(gidsJson, new TypeToken<Queue<Map<String,String>>>(){}.getType());

		String result = HEADER;
		String aux = BASE_VALUES;

		Random r = new Random();

		double confidence;
		int statusDecision;
		int userDecision;
//		int conceptAndNameDecision;
		int numberOfAffected;
		int typeDecision;
		int dateDecision;
		int randomInsertOperation;
		int randomGidInMergeProposal;

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

		List<String> currentGids;
		String rowsAffected;
		for (int id_reg = 1; id_reg < 100; id_reg++) {
			currentGids = new ArrayList<>();
			rowsAffected = "";
			confidence = (r.nextInt(100) * 1.0) + (r.nextInt(9) * 0.1);
			statusDecision = r.nextInt(3);
			userDecision = r.nextInt(3);
			//conceptAndNameDecision = r.nextInt(3);
			numberOfAffected = r.nextInt(4);
			if(numberOfAffected < 2)
				numberOfAffected = 2;
			typeDecision = 0;
			dateDecision = id_reg / 10;
			randomInsertOperation = r.nextInt(30);

			name = NAME_VALUES;
			user = USERS_VALUES[userDecision];
			concept = CONCEPTS_VALUES;
			type = TYPES_VALUES[typeDecision];
			status = STATUSES_VALUES[statusDecision];

			for(int i=0; i<numberOfAffected; i++){
				currentGids.add(gids.poll().get(GID));
			}

			for(int i=0; i<currentGids.size()-1;i++)
				rowsAffected = rowsAffected + "\"" + currentGids.get(i) + "\",";
			rowsAffected = rowsAffected + "\"" + currentGids.get(currentGids.size()-1) + "\"";

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
			else {
				operation = "'" + OPERATION_VALUES + "'";
				for(int i=1 ; i<=13; i++){
					randomGidInMergeProposal = r.nextInt(currentGids.size());
					operation = operation.replaceFirst(GID+i, currentGids.get(randomGidInMergeProposal));
				}
			}
			System.out.println("Doing reg " + id_reg + " ...");

			aux = aux.replace(NAME_VAL, name)
					.replace(ROWS_AFF_VAL, rowsAffected)
					.replace(DESCR_VAL, "description value -- " + name )
					.replace(OBS_VAL, "observations_val -- " + name)
					.replace(TYPE_VAL, type).replace(STATUS_VAL, status)
					.replace(ASSIGNED_TO_VAL, user).replace(CONCEPT_VAL, concept)
					.replace(CONFIDENCE_VAL, ""+confidence)
					.replace(OPERATION_VAL, operation).replace(LAST_UPDATED_VAL, date);

			result = result + aux;

			aux = BASE_VALUES;
		}
		rowsAffected = "";
		currentGids = new ArrayList<>();
		confidence = (r.nextInt(100) * 1.0) + (r.nextInt(9) * 0.1);
		statusDecision = r.nextInt(3);
		userDecision = r.nextInt(3);
//		conceptAndNameDecision = r.nextInt(3);
		numberOfAffected = r.nextInt(4);
		if(numberOfAffected < 2)
			numberOfAffected = 2;
		typeDecision = 0;
		randomInsertOperation = r.nextInt(30);

		name = NAME_VALUES;
		user = USERS_VALUES[userDecision];
		concept = CONCEPTS_VALUES;
		type = TYPES_VALUES[typeDecision];
		status = STATUSES_VALUES[statusDecision];

		for(int i=0; i<numberOfAffected; i++){
			currentGids.add(gids.poll().get(GID));
		}

		for(int i=0; i<currentGids.size()-1;i++)
			rowsAffected = rowsAffected + "\"" + currentGids.get(i) + "\",";
		rowsAffected = rowsAffected + "\"" + currentGids.get(currentGids.size()-1) + "\"";

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

		if((status.equals("ACTIVE") && randomInsertOperation > 20) || status.equals("DISCARDED"))
			operation = "NULL";
		else {
			operation = "'" + OPERATION_VALUES + "'";
			for(int i=1 ; i<=13; i++){
				randomGidInMergeProposal = r.nextInt(currentGids.size());
				operation = operation.replaceFirst(GID+i, currentGids.get(randomGidInMergeProposal));
			}
		}

		aux = aux.replace(NAME_VAL, name)
				.replace(ROWS_AFF_VAL, rowsAffected)
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

