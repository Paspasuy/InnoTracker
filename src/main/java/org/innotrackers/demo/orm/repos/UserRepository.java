package org.innotrackers.demo.orm.repos;

import org.innotrackers.demo.orm.schemas.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserRepository extends CrudRepository<User, String> {
    User findFirstByUsername(String username);
//    public Optional<User> findById(Long id);

//    @Override
//    public <S extends User> S save(S entity);

}
