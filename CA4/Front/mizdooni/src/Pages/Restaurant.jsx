import React from "react";
import "../classes.css";
import { Header } from "../components/Header";
import ResBack from "../Statics/RestaurantBackground.png";
import { ReserveTime } from "../components/ReserveTime";
import Type from "../Statics/Type.png";
import Reviewimg from "../Statics/review.png";
import Clock from "../Statics/Clock.png";
import { ReviewItem } from "../components/ReviewItem";
import { Review } from "../components/Review";
import Pagination from "../Statics/pagination.png";
import Footer from "../components/Footer";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

export const Restaurant = () => {
  return (
    <>
      <Header name={"Reserve"} descreption={"history, plaedfs"} />

      <div className="container d-flex mt-4">
        <div className="col-6 me-3">
          <div className="container-fluid p-0">
            <div className="container mt-4 position-relative">
              <div className="row">
                <div className="col">
                  <img src={ResBack} className="img-fluid" alt="" />
                </div>
                <div className="bg-white-opacity-92 bg-white-opacity-92 position-absolute bottom-0 end-0 w-100 d-flex justify-content-between">
                  <div className="col-7 title-font p-2 rounded">
                    Dizy Ali Daei
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
                  <span className="darkGray fs-14">From 11 AM to 10 PM</span>
                </div>
                <div className="col-4">
                  <img src={Reviewimg} alt="" />
                  <span className="darkGray fs-14">160 Reviews</span>
                </div>
                <div className="col-3">
                  <img src={Type} alt="" />
                  <span className="darkGray fs-14">Dizy</span>
                </div>
              </div>
            </div>
            <div className="row ms-1 mb-3">
              <i className="fa-solid fa-location-dot">
                <span className="gray fs-14">
                  Iran, Boshehr, Vali-e-Asr Square
                </span>
              </i>
              <span className="ms-1 gray fs-10"></span>
            </div>
            <div className="row ms-1 mb-3">
              <p className="gray">
                Ali Daei Dizy restaurant is a cultural oasis in the heart of the
                city, serving up the best of traditional Iranian cuisine. With a
                menu that boasts a diverse selection of flavorful dishes such as
                kebabs, stews, and rice dishes, guests will experience the
                richness and depth of Persian flavors. The ambiance of the
                restaurant is warm and inviting, with intricate Persian rugs
                adorning the walls and the soothing sounds of traditional
                Iranian music playing in the background. Whether you're looking
                to indulge in a delicious meal with friends or simply craving a
                taste of Iran, Ali Daei Dizy restaurant is the perfect spot to
                satisfy your culinary cravings.
              </p>
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
                />
                people, on date
                <input
                  type="date"
                  placeholder="2024-02-18"
                  className="col-3 bg-light buttonRound"
                />
              </div>
            </div>
          </div>
          <div className="row fs-14">
            Available Times for Table #1 (2 seats)
          </div>
          <div className="row">
            <ReserveTime />
            <ReserveTime />
            <ReserveTime />
            <ReserveTime />
            <ReserveTime />
            <ReserveTime />
            <ReserveTime />
            <ReserveTime />
          </div>
          <div className="row fs-14 text-danger pb-2 mb-2">
            You will reserve this table only for one hour, for more time please
            contact the restaurant.
          </div>
          <div className="row">
            <button type="submit" className="btn btn-danger rounded-5">
              Complete the Reservation
            </button>
          </div>
        </div>
      </div>
      <div className="container mb-5">
        <div className="container-fluid d-flex justify-content-between pb-3 mb-3 lightPink cardRadius py-2">
          <div className="col-6">
            <div className="col fs-18 pb-2 mb-2">
              What 160 people are saying
            </div>
            <div className="col-6">
              <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
              <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
              <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
              <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
              <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
              <span className="gray fs-14">4 based on recent ratings</span>
            </div>
          </div>
          <div className="col-6">
            <div className="container-fluid d-flex">
              <ReviewItem name={"Food"} rate={4.5} />
              <ReviewItem name={"Service"} rate={4.5} />
              <ReviewItem name={"Ambience"} rate={4.5} />
              <ReviewItem name={"Overall"} rate={4.5} />
            </div>
          </div>
        </div>

        <div className="container-fluid d-flex justify-content-between pb-3 mb-3">
          <div className="col-4">
            <div className="d-flex align-items-center">
              <span className="align-middle ms-2">160 Reviews</span>
            </div>
          </div>
          <div className="col-4 text-end">
            <button type="button" className="ms-1 btn btn-danger rounded-5">
              Add Review
            </button>
          </div>
        </div>
        <div className="container">
          <Review />
          <Review />
          <Review />
          <Review />
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
    </>
  );
};
