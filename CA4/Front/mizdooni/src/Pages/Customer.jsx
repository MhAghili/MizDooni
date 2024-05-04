import React, { useEffect, useState } from "react";
import "../classes.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Footer from "../components/Footer";
import { Header } from "../components/Header";

export const Customer = () => {
  const userName = localStorage.getItem("username");
  const [reservations, setReservations] = useState([]);
  const [user, setUser] = useState("");
  useEffect(() => {
    async function fetchData() {
      try {
        const reservationsRes = await fetch(
          `http://127.0.0.1:8080/reservations/username=${userName}`
        ).then((res) => res.json());
        setReservations(reservationsRes);
        const userRes = await fetch(
          `http://127.0.0.1:8080/users/${userName}`
        ).then((res) => res.json());
        setUser(userRes);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }
    fetchData();
    console.log("data fetched!");
  }, []);
  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    const minute = date.getMinutes();
    return `${hour}:${minute}`;
  };

  return (
    <>
      <Header name="My Reserves" />
      <div className="container py-5">
        <div className="row">
          <div className="col">
            <span>Your reservations are also emailed to </span>
            <a className="text-decoration-none">
              <span className="text-danger">{user.email}</span>
            </a>
          </div>

          <div className="col text-end">
            Address: {user.address?.city},{user.address?.country}
          </div>
        </div>

        <div className="row mt-3">
          <table className="table">
            <div className="py-3 rounded-top">
              {reservations.length !== 0 ? "My Reservations" : ""}
            </div>
            <tbody>
              {reservations.length !== 0 ? (
                reservations.map((reservation) => (
                  <tr key={reservation.number}>
                    <th scope="row" className="text-muted">
                      {extractHour(reservation.datetime)}
                    </th>
                    <td className="text-danger">
                      {reservation.restaurantName}
                    </td>
                    <td className="text-muted">
                      Table-{reservation.tableNumber}
                    </td>
                    <td className="text-muted">
                      {reservation.numberOfPeople}-seats
                    </td>
                    <td className="text-center">
                      <a href="" className="text-decoration-none text-danger">
                        Cancle
                      </a>
                    </td>
                  </tr>
                ))
              ) : (
                <span className="text-center text-danger fs-4 pt-2 ms-3">
                  No Reservation
                </span>
              )}
            </tbody>
          </table>
        </div>
      </div>
      <Footer />
    </>
  );
};
