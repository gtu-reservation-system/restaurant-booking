    package com.webapp.restaurant_booking.repo;

    import com.webapp.restaurant_booking.models.Favorite;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
        List<Favorite> findByUserId(Long userId);
        Favorite findByUserIdAndRestaurantId(Long userId, Long restaurantId);
    }
