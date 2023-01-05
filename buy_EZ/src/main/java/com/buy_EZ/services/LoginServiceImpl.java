package com.buy_EZ.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buy_EZ.DTO.CustomerDto;
import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.RoleException;
import com.buy_EZ.jwt.JwtUtils;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Cart;
import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.ERole;
import com.buy_EZ.models.Role;
import com.buy_EZ.models.SignupRequest;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.repositories.CustomerCurrentSessionRepo;
import com.buy_EZ.repositories.CustomerRepo;
import com.buy_EZ.repositories.RoleRepo;
import com.buy_EZ.securityConfig.CustomUserDetails;

import net.bytebuddy.utility.RandomString;


@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private AdminCurrentSessionRepo adminCurrentSession;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private CustomerCurrentSessionRepo customerCurrentSessionRepo;
    @Autowired
	private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private BCryptPasswordEncoder encoder;
    
	
	

	
	
	public CustomerDto customerRegister(SignupRequest request) throws CustomerException, RoleException {
		
		User u = customerRepo.findByUsername(request.getUsername());
		if(u==null)
		{

			User user = new User();
			user.setUserId("cu"+"_"+RandomString.make(10));
			user.setUsername(request.getUsername());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setAddress(request.getAddress());
			user.setCountry(request.getCountry());
			user.setEmail(request.getEmail());
			user.setMobileNumber(request.getMobileNumber());
			user.setPassword(encoder.encode(request.getPassword()));
            Set<String> strRoles = request.getRoles();
            Set<Role> roles = new HashSet<>();
            
            
            if(strRoles == null || strRoles.isEmpty())
            {
            	Role userRole = roleRepo.findByName(ERole.ROLE_USER)
            	.orElseThrow(() -> new RoleException("No role found."));
            	
            	roles.add(userRole);
                 	
            }else
            {
            	strRoles.forEach(role -> {
            		if(role.equalsIgnoreCase("admin"))
            		{
            			Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RoleException("No role found."));
            			roles.add(adminRole);
            		}else if(role.equalsIgnoreCase("mod"))
            		{
            			Role modRole = roleRepo.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RoleException("No role found."));
            			roles.add(modRole);
            		}else
            		{
            			Role userRole = roleRepo.findByName(ERole.ROLE_USER)
            	            	.orElseThrow(() -> new RoleException("No role found."));
            	            	
            	            	roles.add(userRole);
            		}
            	});
            }
            user.setRoles(roles);
			Cart c = new Cart(user);
			user.setCart(c);
			customerRepo.save(user);
			CustomerDto cdto = new CustomerDto(user.getUserId(), user.getUsername(), user.getPassword());
			return cdto;
		}
		throw new CustomerException("Custommer Already exists with the username "+request.getUsername());
	}



	@Override
	public Object[] customerLogin(CustomerDto customerDto) throws CustomerException {
		
		// TODO Auto-generated method stub
		
		Optional<User> customerOptional = customerRepo.findById(customerDto.getId());
		if(customerOptional.isPresent())
		{
			if(customerCurrentSessionRepo.findById(customerDto.getId()).isPresent())
			{
				
				customerCurrentSessionRepo.delete(customerCurrentSessionRepo.findById(customerDto.getId()).get());
			}
				
				Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(customerDto.getUsername(), customerDto.getPassword()));
				
				SecurityContext context = SecurityContextHolder.getContext();
						context.setAuthentication(authentication);
				
				   CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
				   ResponseCookie cookie = jwtUtils
						   .generateJwtCookie(userDetails);
	
		
				   CustomerCurrentSession newSession = new CustomerCurrentSession(customerDto.getId(), customerDto.getUsername(), customerDto.getPassword(), LocalDateTime.now());
				   customerCurrentSessionRepo.save(newSession);
				  
				   return new Object[] {cookie, customerOptional.get(), userDetails.getAuthorities()};
			
		}
		throw new CustomerException("No customer present with the id "+customerDto.getId());
	}



	@Override
	public ResponseCookie logout() throws CustomerException {
//		 TODO Auto-generated method stub  
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(username);
		CustomerCurrentSession availableSession =  customerCurrentSessionRepo.findByUsername(username);
		if(availableSession!=null)
		{
		     customerCurrentSessionRepo.delete(availableSession);
		     ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		     return cookie;
		}
		throw new CustomerException("Customer is not logged In");
	}
}
