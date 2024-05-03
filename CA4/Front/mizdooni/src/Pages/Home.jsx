import "../classes.css";
import { Header } from "../components/Header";
import Footer from "../components/Footer";
import { RestaurantCard } from "../components/RestaurantCard";
import mizdooniLogo from "../Statics/mizdooni_logo.png";
import mainBackground from "../Statics/Background.png";
import { About } from "../components/About";
import { useData } from "../Data/DataContext";
const Home = () => {
  const { restaurantData } = useData();
  return (
    <>
      <Header name={"salam"} descreption={"reserve Now"} />
      <div className="position-relative overflow-hidden">
        <div className="container-fluid position-absolute searchForm">
          <div className="row">
            <div className="col-6">
              <img src={mizdooniLogo} className="ms-5 searchFormlogo" alt="" />
            </div>
          </div>
          <div className="row mt-3">
            <div className="col-4">
              <div className="row fs-14 darkGray align-content-center">
                <div className="col-auto bg-light buttonRound me-2 align-self-center py-2">
                  <div>Location</div>
                </div>
                <div className="col-auto bg-light buttonRound me-2 align-self-center py-2">
                  <span>Restaurant</span>
                </div>
                <input
                  className="col-3 bg-light buttonRound me-2 align-self-center py-2"
                  placeholder="Restaurant..."
                />
                <div className="col-auto align-self-center p-0">
                  <button type="button" className="btn btn-danger rounded-5">
                    Search
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <img src={mainBackground} className="mainBackground" alt="" />
      </div>

      <div className="container mt-4">
        <span className="ms-5">Top Restaurants in Mizdooni</span>
        <div className="row justify-content-center">
          {restaurantData.map((restaurant) => (
            <RestaurantCard
              key={restaurant.id}
              name={restaurant.name}
              address={restaurant.address}
              image={restaurant.image}
              type={restaurant.type}
              endTime={restaurant.endTime}
              onClick={() => console.log("clicked")}
            />
          ))}
        </div>
      </div>
      <About />
      <Footer />
    </>
  );
};

export default Home;
