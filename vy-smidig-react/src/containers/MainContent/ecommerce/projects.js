import React, { Component } from 'react';
import { Row, Col, Card, CardBody, Progress, Tooltip } from 'reactstrap';
import { Link, Redirect } from 'react-router-dom';
import { activateAuthLayout } from '../../../store/actions';
import { connect } from 'react-redux';
import Settingmenu from '../Subpages/Settingmenu';


//images
import user1 from '../../../images/users/user-1.jpg';
import user2 from '../../../images/users/user-2.jpg';
import user3 from '../../../images/users/user-3.jpg';
import user4 from '../../../images/users/user-4.jpg';
import user5 from '../../../images/users/user-5.jpg';
import user6 from '../../../images/users/user-6.jpg';
import user7 from '../../../images/users/user-7.jpg';
import user8 from '../../../images/users/user-8.jpg';



class Projects extends Component {

    constructor(props) {
        super(props);
        this.state = {
            capacities: []
        }
    }

    componentDidMount() {
        this.props.activateAuthLayout();

        fetch("https://vy-automatic-ticketing-system.web.app/api/capacity").then(res => res.json()).then(
            (result) => {this.setState({capacities: result})},
            (error) => {this.setState({error})}
        )
    }

    getProgressBarColor(current, max) {
        const percentage = (current / max) * 100;

        if (percentage > 80) {
            return "danger"
        } else {
            if (percentage < 50) {
                return "success"
            } else {
                return "warning"
            }
        }
    }

    getColor(current, max, alpha) {
        const percentage = (current / max) * 100;
        const extra = alpha ? ", 0.2)" : ", 1)"

        if (percentage > 80) {
            return "rgba(255, 0, 0" + extra
        } else {
            if (percentage < 50) {
                return "rgb(39, 134, 109" + extra
            } else {
                return "rgb(255, 255, 0" + extra
            }
        }
    }

    render() {
        const { capacities } = this.state;

        return (
            <React.Fragment>
                <div className="content">
                    <div className="container-fluid">
                        <div className="page-title-box">
                            <Row className="align-items-center">
                                <Col sm="6">
                                    <h4 className="page-title">Train Space </h4>
                                    <ol className="breadcrumb">
                                        <li className="breadcrumb-item"><Link to="#"><i className="mdi mdi-home-outline"></i></Link></li>
                                        <li className="breadcrumb-item active">Train Space</li>
                                    </ol>
                                </Col>
                                <Col sm="6">
                                    <div className="float-right d-none d-md-block">
                                        <Settingmenu />
                                    </div>
                                </Col>
                            </Row>
                        </div>

                        <Row>
                            <Col xl="3" md="6">
                                <Card className="bg-pattern">
                                    <CardBody>
                                        <div className="float-right">
                                            <i className="ion ion-md-train text-primary h4 ml-3"></i>
                                        </div>
                                        <h5 className="font-20 mt-0 pt-1">{capacities.length}</h5>
                                        <p className="text-muted mb-0">Total Trains</p>
                                    </CardBody>
                                </Card>
                            </Col>
                            <Col xl="3" md="6">
                                <Card className="bg-pattern">
                                    <CardBody>
                                        <div className="float-right">
                                            <i className="ion ion-md-train text-primary h4 ml-3"></i>
                                        </div>
                                        <h5 className="font-20 mt-0 pt-1">{(capacities.map(beacon => beacon.trainBeacons.length)).reduce((sum, current) => sum + current, 0)}</h5>
                                        <p className="text-muted mb-0">Total Carriages</p>
                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>

                        <Row>
                            <Col lg="12">
                                <Card>
                                    <CardBody>
                                        <div className="table-responsive project-list">
                                            <table className="table project-table">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">Train ID</th>
                                                        <th scope="col">Train Number</th>
                                                        <th scope="col">Carriage Seats Occupied</th>
                                                        <th scope="col">Total Seats Occupied</th>
                                                        <th scope="col">Total Seats Occupied</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {capacities.map((train, index) => {
                                                        return (
                                                            <tr key={train.trainNumber}>
                                                                <th scope="row">{index + 1}</th>
                                                                <td>{train.trainNumber}</td>
                                                                <td>
                                                                    <div className="carriages">
                                                                        {train.trainBeacons.map((beacon, idx) => {
                                                                            const id = "t" + index + idx;
                                                                            return (
                                                                                <>
                                                                                    <Tooltip placement="top" isOpen={this.state[id]} target={id} toggle={() => this.setState({ [id]: !this.state[id] })}>{beacon.numBoarded + " / " + beacon.carriageCapacity}</Tooltip>
                                                                                    <Link key={index} to="#" id={id}  className="team-member"><span className="rounded-circle thumb-sm" style={{color: this.getColor(beacon.numBoarded, beacon.carriageCapacity, false), backgroundColor: this.getColor(beacon.numBoarded, beacon.carriageCapacity, true)}}>{Math.round((beacon.numBoarded/beacon.carriageCapacity) * 100) + "%"}</span></Link>
                                                                                </>
                                                                            )
                                                                        })}
                                                                    </div>
                                                                </td>
                                                                <td>{train.usedCapacity + " / " + train.maxCapacity}</td>
                                                                <td>
                                                                    <p className="float-right mb-0 ml-3">{Math.round((train.usedCapacity / train.maxCapacity) * 100) + "%"}</p>
                                                                    <Progress className="mt-2" style={{ height: '5px' }} color={this.getProgressBarColor(train.usedCapacity, train.maxCapacity)} value={Math.round((train.usedCapacity / train.maxCapacity) * 100)} />
                                                                </td>
                                                            </tr>
                                                        )
                                                    })}
                                                </tbody>
                                            </table>
                                        </div>


                                        <div className="pt-3">
                                            <ul className="pagination justify-content-end mb-0">
                                                <li className="page-item disabled">
                                                    <Link className="page-link" to="#" tabIndex="-1" aria-disabled="true">Previous</Link>
                                                </li>
                                                <li className="page-item active"><Link className="page-link" to="#">1</Link></li>
                                                <li className="page-item disabled">
                                                    <Link className="page-link" to="#" tabIndex="-1" aria-disabled="true">Next</Link>
                                                </li>
                                            </ul>
                                        </div>


                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>

                    </div>
                </div>
            </React.Fragment>
        );
    }
}


export default connect(null, { activateAuthLayout })(Projects);


