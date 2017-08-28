package org.hibernate.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class MySQLChakaDialect extends  MySQL5InnoDBDialect {
	
	
	public MySQLChakaDialect() {
		super();
		registerFunction( "date_add_interval", new SQLFunctionTemplate( Hibernate.DATE, "date_add(?1, INTERVAL ?2 ?3)" ) );
	}

}
