package net.ion.sst.api_sample.entity;

import lombok.Data;

@Data
public class CounterAndLabel
{
    public String label;
    public int count;

    public CounterAndLabel(String label, int count)
    {
        this.label = label;
        this.count = count;
    }
}
