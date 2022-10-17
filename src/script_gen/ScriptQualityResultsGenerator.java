/*
package script_gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class ScriptQualityResultsGenerator {

	private static final String WEIGHT = "weight";

	private static final String ID_ATTR = "id_attr";

	private static final String ID_ROW = "id_row";

	private static final String _NUM = "_num";
	
	private static final String STATUS = "status";

	private static final String ID_RULE = "id_rule";

	private static final String ID_REG = "id_reg";

	private static final String HEADER = "INSERT INTO MANTENIMIENTO_MAESTROS.QUALITY_RULES_RESULTS (id, id_rule, rule_name, rule_description, rule_conditions, execution_status, execution_date, execution_id, workflow_rocket_id, id_table, id_row, id_atributo, weight) VALUES\n";
	
	private static final String BASE_VALUES = "(id_reg, id_rule, 'RULE_num', 'DESCRP_RULE_num', 'IF NOT NULL', 'status', '2022-06-08', 'execution-id-1234', 1, 1, id_row, id_attr, weight),\n";
	
	public static void main (String [ ] args) {

		String result = HEADER;
		String aux = BASE_VALUES;
		
		Random r = new Random();
		
		int id_rule = 1;
		int id_row = 1;
		int id_att = 1;
		int weight = r.nextInt(0, 7);
		int statusDecision = r.nextInt(0,3);
		String status = statusDecision ==0 ? "KO" : "OK";
		
		for (int id_reg = 1; id_reg < 60000; id_reg++) {
			
			System.out.println("Doing reg " + id_reg + " ...");
			
			aux = aux.replace(ID_REG, ""+id_reg).replace(ID_RULE, ""+id_rule).replace(_NUM, "_"+id_rule).replace(STATUS, status)
					.replace(ID_ROW, ""+id_row).replace(ID_ATTR, ""+id_att).replace(WEIGHT, ""+weight);

			result = result + aux;
			
			weight = id_reg % 60 == 0 ? r.nextInt(0, 8) : weight;
			statusDecision = r.nextInt(0, 3);
			status = statusDecision ==0 ? "KO" : "OK";
			id_rule = id_rule == 300 ? 1 : id_rule + 1 ;
			id_row = id_reg % 300 == 0 ? id_row + 1 : id_row ;
			id_att = (id_reg % 60 == 0 && id_att < 5) ? id_att + 1 : (id_reg % 60 == 0 && id_att == 5) ? 1 : id_att;
			aux = BASE_VALUES;
		}
		
		aux = aux.replace(ID_REG, "60000").replace(ID_RULE, "300").replace(_NUM, "_300").replace(STATUS, "OK")
				.replace(ID_ROW, "200").replace(ID_ATTR, "5").replace(WEIGHT, ""+weight).replace("),", ");");
		
		result = result + aux;
		
		System.out.println(result);
		
		Date d= new Date();
		String filename = "script-" + d.getTime() + ".sql";
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
*/
