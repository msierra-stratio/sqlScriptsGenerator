/*
package script_gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class ScriptQualitySummaryGenerator {


	private static final String ID_ATTR = "id_attribute";

	private static final String ID_ROW = "id_row";

	private static final String _NUM = "_num";
	
	private static final String ID_RULE = "id_rule";

	private static final String ATTR_SCORE = "attribute_score";
	
	private static final String ROW_SCORE = "row_score";
	
	private static final String ATTR_TENDENCY = "attribute_tendency";
	
	private static final String ROW_TENDENCY= "row_tendency";

	private static final String HEADER = "INSERT INTO MANTENIMIENTO_MAESTROS.QUALITY_SUMMARY_SCORE (id_domain, id_table, id_attribute, id_row, id_execution, id_rule, rule_name, rule_weight, attribute_weight, exec_date, attribute_score, row_score, attribute_tendency, row_tendency) VALUES\n";
	
	private static final String BASE_VALUES = "(1, 1, id_attribute, 'id_row', 'execution-test-0001', id_rule, 'rule_num', 5, 5, '2022-07-20 21:00', attribute_score, row_score, attribute_tendency, row_tendency),\n";
	
	public static void main (String [ ] args) {

		String result = HEADER;
		String aux = BASE_VALUES;
		
		Random r = new Random();
		
		int id_rule = 1;
		int id_row = 1;
		int id_att = 1;
		int attr_score = r.nextInt(0, 90);
		int row_score = r.nextInt(0, 70);
		int attr_tendency = r.nextInt(-1, 1);
		int row_tendency = r.nextInt(-1, 1);
		
		for (int id_reg = 1; id_reg < 8000; id_reg++) {
			
			System.out.println("Doing reg " + id_reg + " ...");
			
			aux = aux.replace(ID_ATTR, ""+id_att).replace(ID_ROW, ""+id_row).replace(_NUM, "_"+id_rule).replace(ID_RULE, ""+id_rule)
					.replace(ATTR_SCORE, ""+attr_score).replace(ROW_SCORE, ""+row_score).replace(ATTR_TENDENCY, ""+attr_tendency).replace(ROW_TENDENCY, ""+row_tendency);

			result = result + aux;
			
			id_rule = id_rule == 2 ? 1 : id_rule + 1 ;
			id_row = id_reg % 40 == 0 ? id_row + 1 : id_row ;
			id_att = (id_reg % 2 == 0 && id_att < 20) ? id_att + 1 : (id_reg % 2 == 0 && id_att == 20) ? 1 : id_att;
			aux = BASE_VALUES;
			
			if(id_reg % 2 == 0) {
				attr_score = r.nextInt(0, 90);
				attr_tendency = r.nextInt(-1, 1);
			}
			if(id_reg % 40 == 0) {
				row_score = r.nextInt(0, 90);
				row_tendency = r.nextInt(-1, 1);
			}
		}
		
		aux = aux.replace(ID_ATTR, ""+id_att).replace(ID_ROW, ""+id_row).replace(_NUM, "_"+id_rule).replace(ID_RULE, ""+id_rule)
				.replace(ATTR_SCORE, ""+attr_score).replace(ROW_SCORE, ""+row_score).replace(ATTR_TENDENCY, ""+attr_tendency)
				.replace(ROW_TENDENCY, ""+row_tendency).replace("),", ");");
		
		result = result + aux;
		
		System.out.println(result);
		
		Date d= new Date();
		String filename = "script-quality-summary-" + d.getTime() + ".sql";
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
