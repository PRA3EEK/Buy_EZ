package com.buy_EZ.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    
	
	@Override
	public CustomerCurrentSession adminLogin(AdminDto admin) throws AdminException {
		// TODO Auto-generated method stub
		
User a = customerRepo.findByUsername(admin.getUsername());
		
		if(a!=null) {
			
			
			if(a.getPassword().equals(admin.getPassword())) {
				
				CustomerCurrentSession acs =  customerCurrentSessionRepo.findByUsername(a.getUsername());
				if(acs == null) {
					
						CustomerCurrentSession ac = new CustomerCurrentSession(a.getUserId(), a.getUsername(), new BCryptPasswordEncoder().encode(a.getPassword()), LocalDateTime.now());
						
						customerCurrentSessionRepo.save(ac);
						
						return ac;
					
				}
				
				throw new AdminException("Admin is already logged in");
			}else {
				throw new AdminException("Incorrect Password");
			}
			
		}
		throw new AdminException("No admin available with the username "+admin.getUsername());
		
	}

	
	
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
            
            
            if(strRoles.isEmpty())
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


    public AdminDto adminRegister(Admin admin) throws AdminException{
    	
    	Admin op = adminRepo.findByUsername(admin.getUsername());
    	
    	if(op == null)
    	{
    		admin.setAdminId(UUID.randomUUID().toString());
    		adminRepo.save(admin);
    		AdminDto admindto = new AdminDto(admin.getAdminId(), admin.getUsername(), admin.getPassword());
    		return admindto;
    	}
    	
    	
    	throw new AdminException("Admin already present with the username "+admin.getUsername());
    }
	


	@Override
	public Object[] customerLogin(CustomerDto customerDto) throws CustomerException {
		
		// TODO Auto-generated method stub
		
		Optional<User> customerOptional = customerRepo.findById(customerDto.getId());
		if(customerOptional.isPresent())
		{
			if(!customerCurrentSessionRepo.findById(customerDto.getId()).isPresent())
			{
				
			
				Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(customerDto.getUsername(), customerDto.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				   CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
				   ResponseCookie cookie = jwtUtils
						   .generateJwtCookie(userDetails);
				   CustomerCurrentSession newSession = new CustomerCurrentSession(customerDto.getId(), customerDto.getUsername(), customerDto.getPassword(), LocalDateTime.now());
				   customerCurrentSessionRepo.save(newSession);
				   return new Object[] {cookie, customerOptional.get(), userDetails.getAuthorities()};
			}
		   throw new CustomerException("You are already loggedIn");
		}
		throw new CustomerException("No customer present with the id "+customerDto.getId());
	}
}
