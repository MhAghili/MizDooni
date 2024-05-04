import React, { useEffect, useState } from "react";
import { Header } from "../components/Header";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer";
import "../classes.css";

export const MangerRestaurant = () => {
  const loggedInUser = localStorage.getItem("username");
  const [restaurants, setRestaurants] = useState([]);
  const navigate = useNavigate();
  const manageButtonHndl = (restaurantName_) => {
    navigate("/Manager-Manage", {
      state: { restaurantName: restaurantName_ },
    });
  };

  useEffect(() => {
    async function fetchData() {
      try {
        const restaurantsResponse = await fetch(
          `http://localhost:8080/restaurants/managerName=${loggedInUser}`
        );
        const restaurantsList = await restaurantsResponse.json();

        setRestaurants(restaurantsList);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }
    fetchData();
    console.log("data fetched!");
  }, []);
  return (
    <>
      <Header descreption="Welcome_Admin" name="My Restaurant" />
      <div className="col-6 mx-auto py-5">
        <div className="row mt-3">
          <div className="container mb-4">
            <div className="row bg-light rounded-top align-items-center">
              <div className="col py-3">My Restaurant</div>
              <div className="col text-end">
                <button type="button" className="btn btn-danger rounded-5">
                  Add
                </button>
              </div>
            </div>
          </div>
          <table className="table">
            <tbody>
              {restaurants.map((restaurant) => (
                <tr key={restaurant.name} className="align-items-center">
                  {" "}
                  <th scope="row" className="text-success align-middle">
                    {restaurant.name}
                  </th>
                  <td className="text-danger align-middle">
                    {restaurant.address.city}, {restaurant.address.country}
                  </td>
                  <td className="text-end">
                    <div className="text-decoration-none text-danger">
                      <button
                        onClick={() => manageButtonHndl(restaurant.name)}
                        type="button"
                        className="btn btn-danger rounded-5"
                      >
                        Manage
                      </button>
                    </div>
                  </td>{" "}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <Footer />
    </>
  );
};
