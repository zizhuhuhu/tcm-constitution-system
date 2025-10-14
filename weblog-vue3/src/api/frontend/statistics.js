import instance from "@/axios";
export function getStatisticsInfo() {
    return instance.post("/statistics/info");
}