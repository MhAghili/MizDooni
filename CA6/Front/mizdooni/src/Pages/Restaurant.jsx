import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "../classes.css";
import { Header } from "../components/Header";
import ResBack from "../Statics/RestaurantBackground.png";
import { Reservation, ReserveTime } from "../components/ReserveTime";
import Type from "../Statics/Type.png";
import Reviewimg from "../Statics/review.png";
import Clock from "../Statics/Clock.png";
import { ReviewItem } from "../components/ReviewItem";
import { Review } from "../components/Review";
import Pagination from "../Statics/pagination.png";
import Footer from "../components/Footer";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import AddReviewModal from "../Modals/AddReviewModal";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const Restaurant = () => {
  const [restaurant, setRestaurant] = useState([]);
  const [restaurantReviews, setRestaurantReview] = useState([]);
  const { restaurantName } = useLocation().state;
  const [restaurantAvailableTimes, setAvailableTimes] = useState([]);
  const [reviewItems, setReviewItems] = useState([
    "foodRate",
    "serviceRate",
    "ambianceRate",
    "overallRate",
  ]);
  const [selectedResevationTime, setSelectedReservationTime] = useState(null);
  const [numberOfPeople, setNumberOfPeople] = useState(2);
  const currentDate = new Date().toISOString().split("T")[0];
  const [addReviewModal, showAddReviewModal] = useState(false);
  const handleAddResClick = () => {
    showAddReviewModal(true);
  };

  const handleCloseAddRes = () => {
    fetchData();
    showAddReviewModal(false);
  };

  const [selectedResevationDate, setSelectedReservationDate] =
    useState(currentDate);

  const handleTimeClick = (time) => {
    setSelectedReservationTime(time);
  };

  const handleAddReservation = async () => {
    if (selectedResevationTime && selectedResevationDate && numberOfPeople) {
      try {
        const reqBody = {
          username: localStorage.getItem("username"),
          restaurantName: restaurantName,
          numberOfPeople: numberOfPeople,
          datetime: selectedResevationTime,
          tableNumber: 1,
        };
        const response = await fetch("http://localhost:8080/reserveTable", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          body: JSON.stringify(reqBody),
        });
        console.log(reqBody);
        if (response.ok) {
          toast.success("Table reserved successfully");
          fetchData();
        } else {
          const errorMessage = await response.text();
          toast.error(errorMessage);
          throw new Error(errorMessage);
        }
      } catch (error) {}
    } else {
      console.log("Please fill all the required fields.");
    }
  };

  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    return `${hour}`;
  };
  const calculateStarRating = (score) => {
    const fullStars = Math.floor(score);
    const stars = [];
    for (let i = 0; i < fullStars; i++) {
      stars.push(
        <FontAwesomeIcon icon={faStar} className="fa-sm fa" key={i} />
      );
    }
    return stars;
  };

  const calculateAvarage = (scoreName) => {
    let sum = 0;
    restaurantReviews.forEach((review) => {
      sum += review[scoreName];
    });
    return (sum / restaurantReviews.length).toFixed(1);
  };
  async function fetchData() {
    try {
      const restaurantResponse = await fetch(
        `http://127.0.0.1:8080/restaurants/name=${restaurantName}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      const restaurantReviewRes = await fetch(
        `http://127.0.0.1:8080/reviews/restaurantName=${restaurantName}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          }
        }
      ).then((res) => res.json());

      const restaurantAvailableTimesResponse = await fetch(
        `http://127.0.0.1:8080/tables/available/restaurantName=${restaurantName},tableNumber=${1},date=${selectedResevationDate}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          }
        }
      ).then((res) => res.json());

      const restaurantRes = await restaurantResponse.json();

      setRestaurant(restaurantRes);
      setRestaurantReview(restaurantReviewRes);
      setAvailableTimes(restaurantAvailableTimesResponse);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  }
  // const navigate = useNavigate();
  useEffect(() => {
    fetchData();
    console.log("data fetched!");
  }, [selectedResevationDate]);
  return (
    <>
      <Header name={"My Reserves"} descreption={""} />
      <div className="container d-flex mt-4">
        <div className="col-6 me-3">
          <div className="container-fluid p-0">
            <div className="container mt-4 position-relative">
              <div className="row">
                <div className="col">
                  <img
                    src={restaurant.image}
                    className="restaurantBackground img-fluid rounded-4"
                    alt=""
                  />
                </div>
                <div className="bg-white-opacity-92 bg-white-opacity-92 position-absolute bottom-0 end-0 w-100 d-flex justify-content-between">
                  <div className="col-7 title-font p-2 rounded">
                    {restaurant.name}
                  </div>
                  <div className="col-4 col-md-2 py-2 px-3 bg-green text-center cardRadius m-3 d-flex align-items-center">
                    <span className="text-light m-auto">Open!</span>
                  </div>
                </div>
              </div>
            </div>
            <div className="row ms-1 mb-3">
              <div className="container d-flex">
                <div className="col-5">
                  <img src={Clock} alt="" />
                  <span className="darkGray ms-2 fs-14">
                    From {extractHour(restaurant.startTime)} to{" "}
                    {extractHour(restaurant.endTime)}
                  </span>
                </div>
                <div className="col-4">
                  <img src={Reviewimg} alt="" />
                  <span className="darkGray fs-14 ms-2">
                    {restaurantReviews.length} Reviews
                  </span>
                </div>
                <div className="col-3">
                  <img src={Type} alt="" />
                  <span className="darkGray fs-14 ms-2">{restaurant.type}</span>
                </div>
              </div>
            </div>
            <div className="row ms-1 mb-3">
              <i className="fa-solid fa-location-dot">
                <span className="gray fs-14">
                  {`${restaurant.address?.street} ,
                    ${restaurant.address?.city} ,
                    ${restaurant.address?.country}`}
                </span>
              </i>
              <span className="ms-1 gray fs-10"></span>
            </div>
            <div className="row ms-1 mb-3">
              <p className="gray">{restaurant.description}</p>
            </div>
          </div>
        </div>

        <div className="col-6 ms-3 pt-3 align-items-start">
          <div className="row text-bold pb-2 mb-2 fs-18">Reserve Table</div>
          <div className="row pb-2 mb-2">
            <div className="container d-flex align-items-start">
              <div className="col">
                For
                <input
                  type="number"
                  placeholder="2"
                  className="col-2 bg-light buttonRound"
                  min="0"
                  max="10"
                  value={numberOfPeople}
                  onChange={(e) => setNumberOfPeople(e.target.value)}
                />
                people, on date
                <input
                  type="date"
                  placeholder="2024-02-18"
                  className="col-3 bg-light buttonRound"
                  value={selectedResevationDate}
                  onChange={(e) => setSelectedReservationDate(e.target.value)}
                />
              </div>
            </div>
          </div>
          <div className="row fs-14">
            Available Times for Table #1 (2 seats)
          </div>
          <div className="row">
            {
              <Reservation
                restaurantAvailableTimes={restaurantAvailableTimes}
                handleTimeClick={handleTimeClick}
                selectedTime={selectedResevationTime}
              />
            }
          </div>

          <div className="row fs-14 text-danger pb-2 mb-2">
            You will reserve this table only for one hour, for more time please
            contact the restaurant.
          </div>
          <div className="row">
            <button
              type="submit"
              className="btn btn-danger rounded-5"
              onClick={handleAddReservation}
            >
              Complete the Reservation
            </button>
          </div>
        </div>
      </div>
      <div className="container mb-5">
        <div className="container-fluid d-flex justify-content-between pb-3 mb-3 lightPink cardRadius py-2">
          <div className="col-6">
            <div className="col fs-18 pb-2 mb-2">
              What {restaurantReviews.length} people are saying
            </div>
            <div className="col-6 text-warning">
              {calculateStarRating(calculateAvarage("overallRate"))}
              <span className="gray fs-14">
                {calculateAvarage("overallRate")} based on recent ratings
              </span>
            </div>
          </div>
          <div className="col-6">
            <div className="container-fluid d-flex justify-content-around">
              {reviewItems.map((item) => (
                <ReviewItem
                  key={item}
                  name={item}
                  rate={calculateAvarage(item)}
                />
              ))}
            </div>
          </div>
        </div>

        <div className="container-fluid d-flex justify-content-between pb-3 mb-3">
          <div className="col-4">
            <div className="d-flex align-items-center">
              <span className="align-middle ms-2">
                {restaurantReviews.length} Reviews
              </span>
            </div>
          </div>
          <div className="col-4 text-end">
            <button
              type="button"
              className="ms-1 btn btn-danger rounded-5"
              onClick={handleAddResClick}
            >
              Add Review
            </button>
          </div>
        </div>
        <div className="container">
          {restaurantReviews.map((review) => (
            <Review
              name={review.name}
              key={review.name}
              scores={{
                overall: review.overallRate,
                ambience: review.ambianceRate,
                food: review.foodRate,
                service: review.serviceRate,
              }}
              comment={review.comment}
            />
          ))}
        </div>
        <div className="row justify-content-center">
          <div className="col-auto">
            <div className="d-flex align-items-center justify-content-center">
              <img src={Pagination} alt="" />
            </div>
          </div>
        </div>
      </div>
      <Footer />
      <AddReviewModal
        show={addReviewModal}
        handleClose={handleCloseAddRes}
        restaurantName={restaurantName}
        username={localStorage.getItem("username")}
      />
      <ToastContainer />
    </>
  );
};
