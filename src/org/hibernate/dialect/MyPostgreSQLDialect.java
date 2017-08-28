package org.hibernate.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class MyPostgreSQLDialect extends PostgreSQLDialect //extends  MySQLDialect 
{
	public MyPostgreSQLDialect() 
	{
		 super();
		  registerFunction("date_add", new SQLFunctionTemplate(Hibernate.DATE, 
				  "( cast((?1) as Date) + cast('1 month'  as interval)*(?2) )"));
		  
		  registerFunction("date_diff", new SQLFunctionTemplate(Hibernate.DATE, 
				  "( cast((?1) as Date) - cast('1 month'  as interval)*(?2) )"));
		  
    }

}
