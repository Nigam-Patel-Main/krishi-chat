package online.webnigam.service;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomGroupIdGenrator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		int size = session.createNativeQuery("select * from ggroup").list().size();
		return "GROUP" + (size + 1);
	}

}
