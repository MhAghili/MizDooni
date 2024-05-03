import React, { useState, useEffect } from "react";
import { Header } from "../components/Header";
import { ReserveCard } from "../components/ReserveCard";
import Footer from "../components/Footer";
import { ReserveTableItem } from "../components/ReserveTableItem";
import "../classes.css";
import { useLocation } from "react-router-dom";

export const ManagerManage = () => {
  const { restaurantName } = useLocation().state;
  const [restaurant, setRestaurant] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [tables, setTables] = useState([]);
  useEffect(() => {
    async function fetchData() {
      try {
        const restaurantResponse = await fetch(
          `http://127.0.0.1:8080/restaurants/name=${restaurantName}`
        );
        const tableRses = await fetch(
          `http://127.0.0.1:8080/tables/restaurantName=${restaurantName}`
        ).then((res) => res.json());
        const reservationsRes = await fetch(
          `http://127.0.0.1:8080/reservations/restaurantName=${restaurantName}`
        ).then((res) => res.json());
        setReservations(reservationsRes);
        setRestaurant(restaurantResponse);
        setTables(tableRses);
        const restaurantRes = await restaurantResponse.json();
        console.log(restaurantRes, tableRses);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }
    fetchData();
    console.log("data fetched!");
  }, []);
  return (
    <>
      <Header />
      <div className="h-100">
        <div className="container-fluid text-bg-danger bg-danger d-flex justify-content-between text-light">
          <div className="col-auto ms-5">{restaurant.name}</div>
          <div className="col-10 me-5 text-end px-5">
            Address: {restaurant.address?.street}, {restaurant.address?.city},{" "}
            {restaurant.address?.country}
          </div>
        </div>

        <div className="container-fluid d-flex h-100 justify-content">
          <div className="col-4">
            <div className="container h-100 border-end border-5 border-danger">
              <div className="row align-items-center">
                <div className="col py-3 rounded-top">
                  <span className="font-weight-bold">Reservation List</span>
                </div>
                <div className="col text-end">
                  <span className="col me-1 gray fs-10">
                    Select a table to see its reservations
                  </span>
                </div>
              </div>
              <table className="table">
                <tbody>
                  {reservations.map((reservation) => (
                    <ReserveTableItem
                      date={reservation.datetime}
                      name={reservation.username}
                      tableNumber={reservation.tableNumber}
                      key={reservation.number}
                    />
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          <div className="col lightPink">
            <div className="container bg-light">
              <div className="row d-flex justify-content-start align-items-start">
                <a href="/" className="text-decoration-none text-danger">
                  +Add Table
                </a>
              </div>
              <div className="container align-self-center">
                <div className="row justify-content-center">
                  {tables.map((table) => (
                    <ReserveCard
                      table={table.tableNumber}
                      seat={table.seatsNumber}
                      key={table.tableNumber}
                    />
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer />
    </>
  );
};
