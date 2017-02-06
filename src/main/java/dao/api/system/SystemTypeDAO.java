package dao.api.system;

import org.springframework.data.jpa.repository.JpaRepository;

import model.system.SystemAttribute;

public interface SystemTypeDAO extends JpaRepository<SystemAttribute, Long>
{

}
