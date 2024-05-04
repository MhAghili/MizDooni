import React, { useEffect, useState } from "react";
import "../classes.css";
import { Header } from "../components/Header";
import Footer from "../components/Footer";
import { RestaurantCard } from "../components/RestaurantCard";
import mizdooniLogo from "../Statics/mizdooni_logo.png";
import mainBackground from "../Statics/Background.png";
import { About } from "../components/About";
import { useData } from "../Data/DataContext";
import { Dropdown } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const { restaurantData } = useData();
  const [sortedRestaurantData, setSortedRestaurantData] = useState([]);
  const [searchBy, setSearchBy] = useState("");
  const [searchInput, setSearchInput] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    async function fetchAndUpdateData() {
      const updatedRestaurantData = await Promise.all(
        restaurantData.map(async (restaurant) => {
          const averageOverallScore = await calculateAverageOverallScore(
            restaurant.name
          );
          return { ...restaurant, averageOverallScore };
        })
      );

      updatedRestaurantData.sort(
        (a, b) => b.averageOverallScore - a.averageOverallScore
      );

      setSortedRestaurantData(updatedRestaurantData);
    }

    fetchAndUpdateData();
  }, [restaurantData]);

  async function calculateAverageOverallScore(restaurantName) {
    const reviewsForRestaurant = await fetch(
      `http://127.0.0.1:8080/reviews/restaurantName=${restaurantName}`
    ).then((res) => res.json());

    const totalReviews = reviewsForRestaurant.length;
    const totalOverallScore = reviewsForRestaurant.reduce(
      (acc, curr) => acc + curr.overallRate,
      0
    );
    return totalOverallScore / totalReviews;
  }

  const handleDropdownChange = (selectedValue) => {
    setSearchBy(selectedValue);
  };

  const handleSearch = () => {
    navigate("/SearchResult", {
      state: {
        searchInput: searchInput,
        searchBy: searchBy.toLowerCase(),
      },
    });
  };

  return (
    <>
      <Header name={"My Reserves"}  />
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
                <div className="col-auto bg-light buttonRound me-2 align-self-center p-0 m-0">
                  <Dropdown onSelect={handleDropdownChange}>
                    <Dropdown.Toggle variant="" id="dropdown-basic">
                      {searchBy !== "" ? searchBy : "Search By"}
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                      <Dropdown.Item eventKey="Type">Type</Dropdown.Item>
                      <Dropdown.Item eventKey="Name">Name</Dropdown.Item>
                    </Dropdown.Menu>
                  </Dropdown>
                </div>
                <div className="col-auto bg-light buttonRound me-2 align-self-center py-2">
                  <span>Restaurant</span>
                </div>
                <input
                  className="col-3 bg-light buttonRound me-2 align-self-center py-2"
                  placeholder="Type some..."
                  onChange={(e) => setSearchInput(e.target.value)}
                />
                <div className="col-auto align-self-center p-0">
                  <button
                    type="button"
                    className="btn btn-danger rounded-5"
                    onClick={handleSearch}
                  >
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
          {sortedRestaurantData.map((restaurant) => (
            <RestaurantCard
              key={restaurant.name}
              name={restaurant.name}
              address={restaurant.address}
              image={restaurant.image}
              type={restaurant.type}
              endTime={restaurant.endTime}
              score={restaurant.averageOverallScore}
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
