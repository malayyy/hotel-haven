package com.hotelhaven.repository;
import com.hotelhaven.model.Booking; import com.hotelhaven.model.User; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface BookingRepository extends JpaRepository<Booking,Long>{List<Booking> findByUserOrderByCheckInDesc(User user);}
