package script_gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class ScriptTendencyGenerator {

	private static final String[] EXEC_DATES = {
			"2022-01-06 04:00",
			"2022-01-06 10:00",
			"2022-01-06 16:00",
			"2022-01-06 22:00",
			
			"2022-01-07 04:00",
			"2022-01-07 10:00",
			"2022-01-07 16:00",
			"2022-01-07 22:00",
			
			"2022-01-08 04:00",
			"2022-01-08 10:00",
			"2022-01-08 16:00",
			"2022-01-08 22:00",
			
			"2022-01-09 04:00",
			"2022-01-09 10:00",
			"2022-01-09 16:00",
			"2022-01-09 22:00",
			
			"2022-01-10 04:00",
			"2022-01-10 10:00",
			"2022-01-10 16:00",
			"2022-01-10 22:00",
			
			"2022-01-11 04:00",
			"2022-01-11 10:00",
			"2022-01-11 16:00",
			"2022-01-11 22:00",
			
			"2022-01-12 04:00",
			"2022-01-12 10:00",
			"2022-01-12 16:00",
			"2022-01-12 22:00",
			}; 
	
	
	private static final String SCORE = "score";

	private static final String ID_ATTR = "id_attr";

	private static final String ID_ROW = "id_row";

	private static final String ID_REG = "id_reg";
	
	private static final String EXEC_DATE = "exec_date"; 
	
	private static final String EXEC_ID = "exec_id";

	private static final String HEADER = "INSERT INTO MANTENIMIENTO_MAESTROS.QRR_TENDENCY (id, exec_date, workflow_rocket_id, execution_id, id_table, id_row, id_attr, score) VALUES\n";
	
	private static final String BASE_VALUES = "(id_reg, 'exec_date', 1, 'exec_id', 1, id_row, id_attr, score),\n";
	
	public static void main (String [ ] args) {

		String result = HEADER;
		String aux = BASE_VALUES;
		
		Random r = new Random();
		
		int exec_dates_count = 0;
		String exec_date = EXEC_DATES[exec_dates_count];
		int id_row = 1;
		int id_att = 1;
		int score = r.nextInt(0, 100);
		
		for (int id_reg = 1; id_reg < 140000; id_reg++) {
			
			exec_date = EXEC_DATES[exec_dates_count];
			
			System.out.println("Doing reg " + id_reg + " ...");
			
			aux = aux.replace(ID_REG, ""+id_reg).replace(EXEC_DATE, exec_date).replace(EXEC_ID, "execution-id-000" + exec_dates_count + id_att)
					.replace(ID_ROW, ""+id_row).replace(ID_ATTR, ""+id_att).replace(SCORE, ""+score);

			result = result + aux;
			
			score = r.nextInt(0, 7);
			exec_dates_count = id_reg % 5000 == 0 ? exec_dates_count + 1 : exec_dates_count;
			
			id_row = (id_reg % 25 == 0 && id_row < 200) ? id_row + 1 : (id_reg % 25 == 0 && id_row == 200) ? 1 : id_row;
			
			id_att = (id_reg % 5 == 0 && id_att < 5) ? id_att + 1 : (id_reg % 5 == 0 && id_att == 5) ? 1 : id_att;
			aux = BASE_VALUES;
		}
		
		aux = aux.replace(ID_REG, "140000").replace(EXEC_DATE, exec_date).replace(EXEC_ID, "execution-id-000275")
				.replace(ID_ROW, "200").replace(ID_ATTR, "5").replace(SCORE, ""+score).replace("),", ");");
		
		result = result + aux;
		
		System.out.println(result);
		
		Date d= new Date();
		String filename = "script-tendency-" + d.getTime() + ".sql";
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
