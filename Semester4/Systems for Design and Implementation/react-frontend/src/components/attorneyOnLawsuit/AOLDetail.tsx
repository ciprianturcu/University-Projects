import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { BACKEND_API_URL } from "../../constants";
import { Lawsuit } from "../../models/Lawsuit";
import { AttorneyOnLawsuit } from "../../models/AttorneyOnLawsuit";

export const AOLDetails = () => {
    const {aolId} = useParams();
    const [aol, setAOL] = useState<AttorneyOnLawsuit>();

    useEffect(() => {
        const fetchAOL =async () => {
            const response = await fetch(`${BACKEND_API_URL}/aol/${aolId}/`);
            const aol = await response.json();
            setAOL(aol)
            console.log(aol);
        };
        fetchAOL();
    }, [aolId]);

    return (
        <Container>
        <Card>
            <CardContent>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/attorney-on-lawsuit`}>
                    <ArrowBackIcon />
                </IconButton>{" "}
                <h1 style={{textAlign:"center"}}>Attorney-Lawsuit Details</h1>
                <p style={{textAlign:"left"}}>Attorney: {aol?.attorney.name}</p>
                <p style={{textAlign:"left"}}>Lawsuit: {aol?.lawsuit.description}</p>
                <p style={{textAlign:"left"}}>Attorney Role: {aol?.att_role}</p>
                <p style={{textAlign:"left"}}>Work Type: {aol?.work_type}</p>
                <p style={{textAlign:"left"}}>Description: {aol?.description}</p>
            </CardContent>
            <CardActions>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/attorney-on-lawsuit/${aol?.id}/edit`}>
                    <EditIcon />
                </IconButton>

                <IconButton component={Link} sx={{ mr: 3 }} to={`/attorney-on-lawsuit/${aol?.id}/delete`}>
                    <DeleteForeverIcon sx={{ color: "red" }} />
                </IconButton>
            </CardActions>
        </Card>
    </Container>
    );
};