import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import user1 from "../../../images/users/user-1.jpg";
import "./rightbar.css";

class Rightbar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            startDate: new Date()
        };

        // DatePicker
        this.handleDate = this.handleDate.bind(this);
    }

    handleDate = (date) => {
        this.setState({ startDate: date });
    };

    render() {
        return (
            <React.Fragment>
                 <div className="right-sidebar d-none d-xl-block">
            <div className="slimscroll-menu">
                <div className="px-4 pt-4">
                    <div className="mb-4">
                        <h5 className="font-14">Calender</h5>
                        <div className="dashboard-date-pick" id="date-pick-widget" data-provide="datepicker-inline">
                        <DatePicker
                                inline
                                selected={this.state.startDate}
                                onChange={this.handleDate}
                           />
                        </div>
                    </div>
                </div>
            </div>
        </div>

            </React.Fragment>
        );
    }
}


export default Rightbar;








