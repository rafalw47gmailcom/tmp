package pl.com.tt.testgenerator.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.tt.testgenerator.entieties.Users;
import pl.com.tt.testgenerator.repositories.UsersRepository;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        //  Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // mozna zmieniec na admina
        // grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}
