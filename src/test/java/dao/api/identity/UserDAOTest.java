package dao.api.identity;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.identity.User;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml", "classpath:dispetcher-servlet.xml"})
public class UserDAOTest
{
    @Autowired
    private UserDAO userDAO;
    
    @Test
    public void testFindAllPageable() {

      Pageable pageable = new PageRequest(0, 1, Direction.DESC);
      Page<User> page = userDAO.findAll( pageable );

      assertTrue(page.isFirst());
    }
}
