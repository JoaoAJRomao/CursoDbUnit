package br.ce.wcaquino.dbunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import br.ce.wcaquino.dao.utils.ConnectionFactory;

public class ImportExport {
	public static void main(String[] args) throws Exception {
		exportarBanco("saldo.xml");
//		importarBanco("saida.xml");
	}

	public static void importarBanco(String massa) throws DatabaseUnitException, SQLException, ClassNotFoundException,
			DataSetException, FileNotFoundException {
		DatabaseConnection dbConn = new DatabaseConnection(ConnectionFactory.getConnection());
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet dataSet = builder.build(new FileInputStream("massas" + File.separator + massa));
		List<String> tabelas = obterTabelas();
		desabiliarriggers(tabelas);
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
		habilitarTriggers(tabelas);
	}

	private static void habilitarTriggers(List<String> tabelas) throws ClassNotFoundException, SQLException {
		for (String tabela : tabelas) {
			System.out.println("+++" + tabela);
			Statement stmt = ConnectionFactory.getConnection().createStatement();
			stmt.executeUpdate("ALTER TABLE public." + tabela + " enable trigger all");
			stmt.close();
		}

	}

	private static void desabiliarriggers(List<String> tabelas) throws ClassNotFoundException, SQLException {
		for (String tabela : tabelas) {
			System.out.println("---" + tabela);
			Statement stmt = ConnectionFactory.getConnection().createStatement();
			stmt.executeUpdate("ALTER TABLE public." + tabela + " disable trigger all");
			stmt.close();
		}

	}

	private static List<String> obterTabelas() throws ClassNotFoundException, SQLException {
		List<String> tabelas = new ArrayList<String>();
		ResultSet rs = ConnectionFactory.getConnection().createStatement()
				.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
		while (rs.next()) {
			tabelas.add(rs.getString("table_name"));
		}
		rs.close();
		return tabelas;
	}

	public static void exportarBanco(String massa) throws Exception {
		DatabaseConnection dbConn = new DatabaseConnection(ConnectionFactory.getConnection());
		IDataSet dataSet = dbConn.createDataSet();
		DatabaseSequenceFilter databaseSequenceFilter = new DatabaseSequenceFilter(dbConn);
		FilteredDataSet filteredDataSet = new FilteredDataSet(databaseSequenceFilter, dataSet);
		FileOutputStream fos = new FileOutputStream("massas" + File.separator + massa);
//		FlatXmlDataSet.write(dataSet, fos);
		FlatXmlDataSet.write(filteredDataSet, fos);
	}

}
