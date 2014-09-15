package hexpile;

import client.dataprocessing.BasicBinaryDataSlicer;
import client.dataprocessing.BinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by timmattison on 9/17/14.
 */
public class basicBinaryDataSlicerTests {
    @Test
    public void binaryDataSlicerDoesNotModifyDataOfExactLength() {
        int width = 10;

        Byte[] data = getTestData(width);

        List<BinaryData> binaryDataList = createBinaryDataList(data);

        BasicBinaryDataSlicer basicBinaryDataSlicer = new BasicBinaryDataSlicer();

        List<List<BinaryData>> output = basicBinaryDataSlicer.slice(binaryDataList, width);

        Assert.assertEquals(1, output.size());
        List<BinaryData> slice = output.get(0);
        Assert.assertEquals(width, slice.get(0).data.size());
    }

    @Test
    public void binaryDataSlicerSplitsIntoTwoEqualSlices() {
        int width = 10;

        Byte[] data1 = getTestData(width);
        Byte[] data2 = getTestData(width);
        List<Byte[]> data = new ArrayList<Byte[]>();
        data.add(data1);
        data.add(data2);

        List<BinaryData> binaryDataList = createBinaryDataList(data);

        BasicBinaryDataSlicer basicBinaryDataSlicer = new BasicBinaryDataSlicer();

        List<List<BinaryData>> output = basicBinaryDataSlicer.slice(binaryDataList, width);

        Assert.assertEquals(2, output.size());
        List<BinaryData> slice = output.get(0);
        Assert.assertEquals(width, slice.get(0).data.size());
        slice = output.get(1);
        Assert.assertEquals(width, slice.get(0).data.size());
    }

    @Test
    public void binaryDataSlicerSplitsIntoTwoDifferentSizedSlices1() {
        int width = 10;

        Byte[] data1 = getTestData(width);
        Byte[] data2 = getTestData(width - 5);
        List<Byte[]> data = new ArrayList<Byte[]>();
        data.add(data1);
        data.add(data2);

        List<BinaryData> binaryDataList = createBinaryDataList(data);

        BasicBinaryDataSlicer basicBinaryDataSlicer = new BasicBinaryDataSlicer();

        List<List<BinaryData>> output = basicBinaryDataSlicer.slice(binaryDataList, width);

        Assert.assertEquals(2, output.size());
        List<BinaryData> slice = output.get(0);
        Assert.assertEquals(width, slice.get(0).data.size());
        slice = output.get(1);
        Assert.assertEquals(width - 5, slice.get(0).data.size());
    }

    @Test
    public void binaryDataSlicerSplitsIntoTwoDifferentSizedSlices2() {
        int width = 10;

        Byte[] data1 = getTestData(width);
        Byte[] data2 = getTestData(3);
        Byte[] data3 = getTestData(5);
        List<Byte[]> data = new ArrayList<Byte[]>();
        data.add(data1);
        data.add(data2);
        data.add(data3);

        List<BinaryData> binaryDataList = createBinaryDataList(data);

        BasicBinaryDataSlicer basicBinaryDataSlicer = new BasicBinaryDataSlicer();

        List<List<BinaryData>> output = basicBinaryDataSlicer.slice(binaryDataList, width);

        Assert.assertEquals(2, output.size());
        List<BinaryData> slice = output.get(0);
        Assert.assertEquals(width, slice.get(0).data.size());
        slice = output.get(1);
        Assert.assertEquals(3, slice.get(0).data.size());
        Assert.assertEquals(5, slice.get(1).data.size());
    }

    private List<BinaryData> createBinaryDataList(Byte[] data) {
        BinaryData binaryData = new BinaryData();
        binaryData.name = "Name";
        binaryData.data = Arrays.asList(data);

        List<BinaryData> binaryDataList = new ArrayList<BinaryData>();
        binaryDataList.add(binaryData);
        return binaryDataList;
    }

    private List<BinaryData> createBinaryDataList(List<Byte[]> dataList) {
        List<BinaryData> binaryDataList = new ArrayList<BinaryData>();

        for (Byte[] data : dataList) {
            BinaryData binaryData = new BinaryData();
            binaryData.name = "Name";
            binaryData.data = Arrays.asList(data);

            binaryDataList.add(binaryData);
        }

        return binaryDataList;
    }

    private Byte[] getTestData(int width) {
        Byte[] data = new Byte[width];

        for (int loop = 0; loop < width; loop++) {
            data[loop] = (byte) loop;
        }
        return data;
    }
}
