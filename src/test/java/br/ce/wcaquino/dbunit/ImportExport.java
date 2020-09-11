package br.ce.wcaquino.dbunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;

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
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
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
